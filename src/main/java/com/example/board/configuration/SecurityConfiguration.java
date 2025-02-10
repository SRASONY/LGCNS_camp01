package com.example.board.configuration;

import com.example.board.security.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Spring Security를 활성화하는 역할
public class SecurityConfiguration {
    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    //SecurityFilterChain을 Bean으로 등록하여 Spring Security의 필터 체인을 설정
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/home","/join","/joinProc").permitAll()
                         // 공개페이지(누구나 접근가능)

                        .requestMatchers("/board/**", "/api/**").hasAnyRole("ADMIN", "USER")
                        //"ADMIN" 또는 "USER" 역할을 가진 사용자만 /board/** 및 /api/** 경로에 접근 가능

                        .anyRequest().authenticated()
                        //위에서 지정하지 않은 나머지 모든 요청은 인증된 사용자만 접근 가능하도록 설정
                        //즉, 로그인을 하지 않은 사용자는 접근할 수 없음
                );
        //사용자 정의 로그인 페이지와 로그인 요청 주소를 등록
        http.formLogin(auth -> auth
                .loginPage("/login") //로그인 페이지 경로를 설정
                .loginProcessingUrl("/loginProc") // 로그인 처리를 담당하는 URL을 설정
                .permitAll()
                //.defaultSuccessUrl("/board") // 로그인 시 해당 URL로 이동
                .successHandler(successHandler)

        );
        // 개발단계에서 임시적으로 Disable
        http.csrf(auth -> auth.disable());

        return http.build();
    }
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

