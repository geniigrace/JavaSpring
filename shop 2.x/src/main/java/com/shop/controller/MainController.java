package com.shop.controller;

import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainItemDto;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor //final, not-null 을 객체화
public class MainController {

    private final ItemService itemService;

    @GetMapping(value = "/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model ){
        //ItemSearchDto : 쿼리관련 //Optinal<Integer> page : 페이지관련 //Model : html 화면으로 올릴 모델

        //페이지는 5개씩 만들고, 페이지가 있으면 그걸 쓰고 없으면 0
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);

        //메인 상단 header에 검색부분에서 검색어가 없으면 아무것도 안띄우고 밑으로 내려감
        if(itemSearchDto.getSearchQuery() == null){
            itemSearchDto.setSearchQuery("");
        }


        Page <MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        return "main";
    }
}
