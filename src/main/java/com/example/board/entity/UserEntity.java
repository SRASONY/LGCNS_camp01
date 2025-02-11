package com.example.board.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_jpa_user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //데이터베이스에서 자동으로 증가하는 기본 키(Primary Key, PK)를 설정
    private int seq;

    @Column(unique = true)
    private String username;

    private String password;
    private String name;
    private String email;

    private String role;
}

