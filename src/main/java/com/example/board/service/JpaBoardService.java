package com.example.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;

public interface JpaBoardService {
    List<BoardEntity> selectBoardList();
    BoardEntity selectBoardDetail(int boardIdx);
    void insertBoard(BoardEntity boardEntity, MultipartHttpServletRequest request) throws Exception;
    void updateBoard(BoardEntity boardEntity);
    void deleteBoard(int boardIdx);
    BoardFileEntity selectBoardFileInfo(int idx, int boardIdx);
}

