package com.shop.dto;


import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {

    private String searchDateType; //등록시점을 기준으로 선택할 수 있음

    private ItemSellStatus searchSellStatus;

    private String searchBy; //상품명이나 등록자아이디 등으로 검색기준잡음

    private String searchQuery = "";

}
