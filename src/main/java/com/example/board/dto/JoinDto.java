package com.example.board.dto;

import lombok.Data;

@Data
public class JoinDto {
    private String username;
    private String password;
    private String passwordConfirm;
    private String name;
    private String email;

    //비밀번호와 비밀번호 확인 값이 일치하는지 검증하는 함수
    public boolean checkPassword() {
        return this.password != null && this.password.equals(this.passwordConfirm);
    }
}
