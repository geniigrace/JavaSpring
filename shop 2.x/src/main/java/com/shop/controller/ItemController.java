package com.shop.controller;

import com.shop.dto.ItemFormDto;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // 상품등록 페이지를 위한 맵핑
    @GetMapping(value="/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }

    // 상품 등록 : 상품 및 파일을 등록하기 위한 post 맵핑
    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){
        if(bindingResult.hasErrors()){
            return "/item/itemForm";
        }

        //get(0)이 비워져있고 id도 null 이면 첫번째 상품이미지가 등록되지 않은 상황임
        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다."); //itemForm.html 윗단에 에러 aleter 으로 뜸
            return "item/itemForm";
        }
        try{
            itemService.saveItem(itemFormDto, itemImgFileList);
        }
        catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/";
    }


    //상품 파일 수정을 위한 추가
    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId")Long itemId, Model model){
        try{
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        }
        catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", "new ItemFormDto");
            return "item/itemForm";
        }

        return "item/itemForm";
    }


}
