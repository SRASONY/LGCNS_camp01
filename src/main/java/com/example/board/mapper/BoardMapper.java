//package com.example.board.mapper;
//
//import com.example.board.dto.BoardDto;
//import com.example.board.dto.BoardFileDto;
//import org.apache.ibatis.annotations.Mapper;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//@Mapper
//public interface BoardMapper {
//    List<BoardDto> selectBoardList();
//    void insertBoard(BoardDto boardDto);
//    BoardDto selectBoardDetail(int boardIdx);
//    void updateHitCnt(int boardIdx);
//    void updateBoard(BoardDto boardDto);
//    void deleteBoard(BoardDto boardDto);
//
//    void insertBoardFileList(List<BoardFileDto> fileInfoList);
//    List<BoardFileDto> selectBoardFileList(int boardIdx);
//    BoardFileDto selectBoardFileInfo(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
//
//}
