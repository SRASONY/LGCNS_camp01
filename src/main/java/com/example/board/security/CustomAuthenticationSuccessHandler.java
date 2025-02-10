package com.example.board.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.board.entity.UserEntity;
import com.example.board.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//로그인 성공 시 리다이렉션을 포함해서 추가적인 작업을 수행할 경우
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 로그인한 사용자 정보 가져오기
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // DB에서 사용자 정보 조회
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());

        // 세션에 사용자 정보 저장
        request.getSession().setAttribute("user", userEntity);

        // 로그인 성공 후 홈페이지로 리다이렉트
        response.sendRedirect("/");
    }
}
