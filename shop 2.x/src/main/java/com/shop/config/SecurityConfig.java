package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //설정
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { //상속받으면 자동포함 메서드를 오버라이딩 할수 있다

    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/");


        http.authorizeRequests()//인증요청이 왔는데

                .mvcMatchers("/","/members/**","/item/**","/images/**").permitAll() //해당경로는 모든 경우
                .mvcMatchers("/admin/**").hasRole("ADMIN") //admin 경로는 권한이 admin인 경우
                .anyRequest().authenticated();

        http.exceptionHandling() //예외 핸들링은 만들어놓은 CAEP를 통해 함
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

    }

    @Bean //원두: 빈객체 -> SpringContainer에 들어감 -> 이 객체를 하나로 돌려씀:싱글턴
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //비밀번호를 암호화 하는 해시함수
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception{

        web.ignoring().antMatchers("/css/**","/js/**","/img/**"); //이 경로에 있는 것들은 시큐리티에서 제외
    }

}
