package com.example.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_jpa_file")
@Data
@DynamicUpdate //  업데이트 시 변경된 필드만 반영
public class BoardFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idx;

    // private int boardIdx;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String storedFilePath;

    @Column(nullable = false)
    private long fileSize;

    @JsonBackReference // 참조(자식엔티티)
    //LAZY ;  부모 엔티티를 조회할 때 자식 엔티티는 조회하지 않음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_idx")
    private BoardEntity board;
}

