//package com.example.board.service;
//
//import java.util.Iterator;
//import java.util.List;
//
//import com.example.board.common.FileUtils;
//import com.example.board.dto.BoardFileDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//
//import com.example.board.dto.BoardDto;
//import com.example.board.mapper.BoardMapper;
//import lombok.extern.slf4j.Slf4j;
//
//// @Transactional
//@Slf4j
//@Service
//
//public class BoardServiceImpl implements BoardService {
//    // Mapper 인터페이스 호출 -> 의존성 주입
//    @Autowired
//    private BoardMapper boardMapper;
//
//    @Autowired
//    private FileUtils fileUtils;
//
//    // selectBoardList() 메서드는 boardMapper가 가지고 있는 selectBoardList() 메서드를 호출해서 실행 결과를 반환
//    @Override
//    public List<BoardDto> selectBoardList() {
//        return boardMapper.selectBoardList();
//    }
//
//    @Override
//    public void insertBoard(BoardDto boardDto, MultipartHttpServletRequest request) {
//        // 로그인한 사용자를 글쓴이로 설정
//        // TODO. 로그인한 사용자의 ID로 변경
//        boardDto.setCreatedId("hong");
//        boardMapper.insertBoard(boardDto);
//
//        try {
//            // 첨부 파일을 디스크에 저장하고, 첨부 파일 정보를 반환
//            List<BoardFileDto> fileInfoList = fileUtils.parseFileInfo(boardDto.getBoardIdx(), request);
//
//            // 첨부 파일 정보를 DB에 저장
//            if (!CollectionUtils.isEmpty(fileInfoList)) {
//                boardMapper.insertBoardFileList(fileInfoList);
//            }
//        } catch(Exception e) {
//            log.error(e.getMessage());
//        }
//    }
//
//    @Override
//    public BoardDto selectBoardDetail(int boardIdx) {
//        boardMapper.updateHitCnt(boardIdx); // 조회수
//
//        BoardDto boardDto = boardMapper.selectBoardDetail(boardIdx); // 상세조회
//        if(boardDto != null){
//            List<BoardFileDto> boardFileInfoList = boardMapper.selectBoardFileList(boardIdx); // 첨부파일조회
//            boardDto.setFileInfoList(boardFileInfoList);
//        }
//        return boardDto;
//    }
//
//    @Override
//    public BoardFileDto selectBoardFileInfo(int idx, int boardIdx) {
//        return boardMapper.selectBoardFileInfo(idx, boardIdx);
//    }
//
//
//    @Override
//    public void updateBoard(BoardDto boardDto) {
//        //로그인한 사용자를 글쓴이로 설정
//        //TODO.로그인한 사용자의 ID로 변경
//        boardDto.setUpdatorId("go");
//        boardMapper.updateBoard(boardDto);
//    }
//
//    @Override
//    public void deleteBoard(int boardIdx) {
//        BoardDto boardDto = new BoardDto();
//        boardDto.setBoardIdx(boardIdx);
//        //TODO.로그인한 사용자의 ID로 변경
//        boardDto.setUpdatorId("go");
//
//        boardMapper.deleteBoard(boardDto);
//        }
//
//
//}
