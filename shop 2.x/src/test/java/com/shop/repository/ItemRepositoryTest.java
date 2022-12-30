package com.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;

import com.shop.entity.QItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {
    @Autowired //자동와이어
    ItemRepository itemRepository;
    @PersistenceContext
    EntityManager em;


    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }
    public void createItemList()
    {
        for(int i = 1; i<=10;i++)
        {
            Item item = new Item();
            item.setItemNm("테스트 상품"+i);
            item.setPrice(10000+i);
            item.setItemDetail("테스트 상품 상세 설명"+i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }
    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.
                findByItemNmOrItemDetail("테스트 상품1",
                        "테스트 상품 상세 설명5");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@nativeQuery를 이용한 상품 조회 테스트")
    public void findByItemDetailByNativeTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회테스트1")
    public void queryDslTest()
    {
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명"+ "%"))
                .orderBy(qItem.price.desc());
        List<Item> itemList = query.fetch(); //내용을 뽑아줌

        for(Item item : itemList)
            System.out.println(item.toString());
        }

        public void createItemList2(){
        for(int i=1; i<=5; i++){
            Item item = new Item();
            item.setItemNm("테스트 상품 "+i);
            item.setPrice(10000+i);
            item.setItemDetail("테스트상품 상세설명 "+i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
        for(int i=6; i<=10; i++){
            Item item=new Item();
            item.setItemNm("테스트 상품 "+i);
            item.setPrice(10000+i);
            item.setItemDetail("테스트상품 상세설명 "+i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
        }

        @Test
    @DisplayName("상품 Querydsl 조회테스트 2")
    public void queryDslTest2(){
        this.createItemList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder(); //QueryDSL 사용하기 위해 선언
        QItem item = QItem.item;

        String itemDetail = "테스트상품 상세설명";
        int price = 10003;
        String itemSellStat = "SELL";

        //테스트 상품 상세설명이 포함된 경우
            booleanBuilder.and(item.itemDetail.like("%"+itemDetail+"%"));
            booleanBuilder.and(item.price.gt(price)); //gt : 보다 큰 것

            if(StringUtils.equals(itemSellStat,ItemSellStatus.SELL)){ //itemSellStat도 SELL로 ItemSellStatus.SELL과 동일할 때
                booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
            } //itemSellStatus가 Sell 인 경우

            //페이지당 조회할 데이터의 개수
            Pageable pageable = PageRequest.of(0,5); //0부터 5까지 뺌 : 5개씩 1페이지
            //조건에 맞는 데이터를 Page 객체로 받아옴
            Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
            //findAll 은 QuerydslPredicateExecutor<Item>을 상속받았기 때문에 사용이 가능한 것

            System.out.println("total elements : "+itemPagingResult.getTotalElements());

            List<Item> resultItemList = itemPagingResult.getContent();
            for(Item resultItem : resultItemList){
                System.out.println(resultItem.toString());
            }

        }
    }
