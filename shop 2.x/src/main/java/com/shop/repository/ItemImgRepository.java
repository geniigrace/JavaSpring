package com.shop.repository;

import com.shop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//업로드한 IMG 를 위한 레포지토리 생성
public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
}
