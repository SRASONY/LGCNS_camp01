package com.example.board.service;

import com.example.board.common.FileUtils;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import com.example.board.repository.JpaBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Optional;

@Service
public class JpaBoardServiceImpl implements JpaBoardService {
    @Autowired
    private JpaBoardRepository jpaBoardRepository;

    @Autowired
    FileUtils fileUtils;

    @Override
    public List<BoardEntity> selectBoardList(){
        return jpaBoardRepository.findAllByOrderByBoardIdxDesc(); //데이터를 정렬해서 가져오기(쿼리메서드로 정의한 것)
    }
    @Override
    public BoardEntity selectBoardDetail(int boardIdx) {
        Optional<BoardEntity> optional = jpaBoardRepository.findById(boardIdx);
        if (optional.isPresent()) {
            BoardEntity board = optional.get();

            board.setHitCnt(board.getHitCnt() + 1);
            jpaBoardRepository.save(board);

            return board;
        } else {
            throw new RuntimeException();
        }
    }
    @Override
    public void insertBoard(BoardEntity boardEntity, MultipartHttpServletRequest request) throws Exception {
        boardEntity.setCreatedId("admin");
        List<BoardFileEntity> list = fileUtils.parseFileInfo2(boardEntity.getBoardIdx(), request);
        if (!CollectionUtils.isEmpty(list)) {
            boardEntity.setFileInfoList(list);
        }
        jpaBoardRepository.save(boardEntity);
    }
    @Override
    public void updateBoard(BoardEntity boardEntity) {
        BoardEntity existingBoard = jpaBoardRepository.findById(boardEntity.getBoardIdx()).orElse(null);
        if (existingBoard != null) {
            boardEntity.setFileInfoList(existingBoard.getFileInfoList());
        }
        jpaBoardRepository.save(boardEntity);
    }

    @Override
    public void deleteBoard(int boardIdx) {
        jpaBoardRepository.deleteById(boardIdx);
    }
    @Override
    public BoardFileEntity selectBoardFileInfo(int idx, int boardIdx) {
        return jpaBoardRepository.findBoardFile(boardIdx, idx);
    }
}


