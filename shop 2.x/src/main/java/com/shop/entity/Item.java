package com.shop.entity;

import com.shop.constant.ItemSellStatus;

import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity{
    @Id //기본키
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동을 1씩 증가
    private Long id; // 상품코드

    @Column(nullable = false, length = 50)
    private String itemNm; // 상품명

    @Column(name="price", nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int stockNumber; // 재고 수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상태

    //private LocalDateTime regTime; // 등록시간

    //private  LocalDateTime updateTime; // 수정시간

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="member_item",
            joinColumns = @JoinColumn(name="member_id"),
            inverseJoinColumns = @JoinColumn(name="item_id")
    )
    private List<Member> member;

    //상품 파일수정을 위한 추가/../
    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    //주문시 주문수량보다 재고수량이 적을때 발생하는 예외처리
    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber;

        if(restStock < 0){
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량 : " + this.stockNumber + " )");
        }

        this.stockNumber = restStock;
    }

}
