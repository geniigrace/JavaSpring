//Thymeleaf test 를 위한 컨트롤러

package com.shop.controller;

import com.shop.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller //클래스를 컨트롤러로 선언
@RequestMapping(value = "/thymeleaf") // 요청경로가 /thymeleaf 로 시작하면 이 클래스 호출
public class ThymeleafExController {

    @GetMapping(value = "/ex01") //get 방식중에 경로가 /thymeleaf/ex01 이면 아래 함수 실행
    public String thymeleafExample01(Model model){ //Model(Spring Framwork Model) View에 사용할 모델

        //모델에 속성 추가
        model.addAttribute("data","타임리프 예제 입니다."); //속성이름 "data", 값 "타임리프~입니다."
        return "thymeleafEx/thymeleafEx01"; //문자열을 리턴하면 그 경로에 맞는 HTML 이 호출됨

    }

    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model){

        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품 1");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto",itemDto);


        return "thymeleafEx/thymeleafEx02";

    }
}


