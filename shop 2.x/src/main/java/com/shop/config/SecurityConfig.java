package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //설정
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { //상속받으면 자동포함 메서드를 오버라이딩 할수 있다

    @Override
    protected void configure(HttpSecurity http) throws Exception{

    }

    @Bean //원두: 빈객체 -> SpringContainer에 들어감 -> 이 객체를 하나로 돌려씀:싱글턴
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //비밀번호를 암호화 하는 해시함수
    }
}
