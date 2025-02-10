package com.example.board.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_jpa_board")
@Data
@DynamicUpdate  // 변경된 필드만 업데이트
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int boardIdx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private int hitCnt = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDt;

    @Column(nullable = false, updatable = false)
    private String createdId;

    @UpdateTimestamp
    private LocalDateTime updatorDt;

    private String updatorId;

    // 순환참조 문제를 해결하기 위해 사용 -> JPA의 양방향 관걔에서 JSON 직렬화 시 무한루프 발생하는 것 방지
    @JsonManagedReference // 주인(부모 엔티티)
    // EAGER : 부모 엔티티를 조회할 때 자식 엔티티도 함께 조회
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_idx")
    private List<BoardFileEntity> fileInfoList;
}
