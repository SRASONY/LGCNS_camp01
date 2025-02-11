package com.example.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.board.dto.JoinDto;
import com.example.board.service.JoinService;

@Controller
public class JoinController {
    @Autowired
    JoinService joinService;

    @GetMapping("/join")
    public String join() {
        return "/join";
    }

    @PostMapping("/joinProc")
    public String joinProc(JoinDto joinDto) {
        if (joinService.joinProcess(joinDto)) {
            return "redirect:/login"; //회원가입 성공 시 로그인 페이지로 이동
        } else {
            return "redirect:/join"; // 회원가입 실패 시 다시 회원가입 페이지로 이동
        }
    }
}

