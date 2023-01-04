package com.shop.entity;

import com.shop.constant.ItemSellStatus;

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
public class Item {
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

    private LocalDateTime regTime; // 등록시간

    private  LocalDateTime updateTime; // 수정시간

    @ManyToMany
    @JoinTable(
            name="member_item",
            joinColumns = @JoinColumn(name="member_id"),
            inverseJoinColumns = @JoinColumn(name="item_id")
    )
    private List<Member> member;
}
