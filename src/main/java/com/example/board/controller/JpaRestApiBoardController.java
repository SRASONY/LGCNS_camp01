package com.example.board.controller;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.board.dto.BoardListResponse;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import com.example.board.service.JpaBoardService;
import jakarta.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2")
@Tag(name = "Board API", description = "게시판 관련 API")
public class JpaRestApiBoardController {

    @Autowired
    private JpaBoardService boardService;

    // 목록 조회
    @GetMapping("/board")
    @Operation(summary = "게시판 목록 조회", description = "등록된 모든 게시판 목록을 조회합니다.")
    public ResponseEntity<Object> openBoardList() throws Exception {
        List<BoardListResponse> results = new ArrayList<>();
        try {
            List<BoardEntity> boardList = boardService.selectBoardList();
            boardList.forEach(dto -> results.add(new ModelMapper().map(dto, BoardListResponse.class)));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("목록 조회 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 저장 처리
    @PostMapping(value = "/board", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시판 저장", description = "새로운 게시판을 등록합니다.")
    public ResponseEntity<Object> insertBoard(@RequestParam("board") String boardData, MultipartHttpServletRequest request) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BoardEntity boardDto = objectMapper.readValue(boardData, BoardEntity.class);
        Map<String, String> result = new HashMap<>();
        try {
            boardService.insertBoard(boardDto, request);
            result.put("message", "게시판 저장 성공");
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch(Exception e) {
            result.put("message", "게시판 저장 실패");
            result.put("description", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    // 상세 조회
    @GetMapping("/board/{boardIdx}")
    @Operation(summary = "게시판 상세 조회", description = "게시판 ID를 입력하면 해당 게시글 정보를 반환합니다.")
    public ResponseEntity<Object> openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
        BoardEntity boardEntity = boardService.selectBoardDetail(boardIdx);
        if (boardEntity == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", HttpStatus.NOT_FOUND.value());
            result.put("name", HttpStatus.NOT_FOUND.name());
            result.put("message", "게시판 번호 " + boardIdx + "와 일치하는 게시물이 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(boardEntity);
        }
    }

    // 수정 처리
    @PutMapping("/board/{boardIdx}")
    @Operation(summary = "게시판 수정", description = "게시판 ID를 입력하고 데이터를 수정합니다.")
    public void updateBoard(@PathVariable("boardIdx") int boardIdx, @RequestBody BoardEntity boardEntity) throws Exception {
        boardEntity.setBoardIdx(boardIdx);
        boardService.updateBoard(boardEntity);
    }

    // 삭제 처리
    @DeleteMapping("/board/{boardIdx}")
    @Operation(summary = "게시판 삭제", description = "게시판 ID를 입력하면 해당 게시글을 삭제합니다.")
    public void deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
    }

    // 첨부파일 다운로드
    @GetMapping("/board/file")
    @Operation(summary = "첨부파일 다운로드", description = "게시판 ID와 파일 ID를 입력하면 첨부파일을 다운로드합니다.")
    public void downloadBoardFile(@RequestParam("idx") int idx, @RequestParam("boardIdx") int boardIdx, HttpServletResponse response) throws Exception {
        BoardFileEntity boardFileEntity = boardService.selectBoardFileInfo(idx, boardIdx);
        if (ObjectUtils.isEmpty(boardFileEntity)) {
            return;
        }

        Path path = Paths.get(boardFileEntity.getStoredFilePath());
        byte[] file = Files.readAllBytes(path);

        response.setContentType("application/octet-stream");
        response.setContentLength(file.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(boardFileEntity.getOriginalFileName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(file);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
