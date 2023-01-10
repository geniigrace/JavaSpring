package com.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDto {

    private Long id;
    private String itemNm;

    private String itemDetail;

    private String imgUrl;

    private Integer price;

    @QueryProjection // QureyDSL 조회시 MainItemDto 객체로 바로 오도록 활용 : DTO가 QDTO로 나올 수 있음
    public MainItemDto(Long id, String itemNm, String itemDetail, String imgUrl, Integer price){

        this.id=id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;

    }
}
