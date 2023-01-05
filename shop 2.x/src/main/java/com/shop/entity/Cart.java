package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="cart")
@Getter
@Setter
@ToString
public class Cart {

    @Id
    @Column(name="cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) //1:1 맵핑 (Cart : member)
    @JoinColumn(name="member_id") //맵핑 할 외래키를 지정 //외래키 이름을 지정하지 않으면 JPA가 알아서 지정하지만 원하는 이름이 아닐수도 있다.
    private Member member; //연결할 엔티티 명을 씀

}
