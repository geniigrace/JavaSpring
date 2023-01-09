package com.shop.service;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.dto.ItemSearchDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    //상품등록 : 상품 및 상품 이미지를 등록하기 위한 서비스
    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{

        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 등록
        for(int i=0; i<itemImgFileList.size(); i++){

            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i==0)
                itemImg.setRepImgYn("Y");
            else
                itemImg.setRepImgYn("N");

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();
    }

    // 상품 파일 수정을 위한 추가
    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId){

        //이미지 정보 받아오기
          List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
             /* select * from item_img where itemi=2 orderby itemid asc */

          List<ItemImgDto> itemImgDtoList = new ArrayList<>();

          //Entity로 받은 데이터를 DTO로 넣어줌
          for(ItemImg itemimg : itemImgList){
              ItemImgDto itemImgDto = ItemImgDto.of(itemimg);
              itemImgDtoList.add(itemImgDto);
          }

        //이미지 제외한 데이터 받아오기
          //레포지에서 받아온 정보가 Entity에 저장
         Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
          //Entity의 데이터를 DTO에 담음
         ItemFormDto itemFormDto = ItemFormDto.of(item);

         //등록되어있던 itemImgDTO를 itemFormDto로 넣어줌
         itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);

        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        for(int i=0; i<itemImgFileList.size(); i++){
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));

        }
        return item.getId();
    }

    //상품관리 페이지
    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

}
