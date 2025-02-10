package com.example.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;

@Repository
public interface JpaBoardRepository extends CrudRepository<BoardEntity, Integer> {
    // 1) 쿼리 메서드
    List<BoardEntity> findAllByOrderByBoardIdxDesc();

    // 2) @Query 어노테이션으로 실행할 쿼리를 직접 정의
    // SQL과 형식은 같지만 SQL문은 아니고 객체 형태의 JPQL임
    @Query("SELECT file FROM BoardFileEntity file WHERE file.board.boardIdx = :boardIdx AND file.idx = :idx")
    BoardFileEntity findBoardFile(@Param("boardIdx") int boardIdx, @Param("idx") int idx);
}


