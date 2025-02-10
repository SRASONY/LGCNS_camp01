//package com.example.board.controller;
//
//import com.example.board.dto.BoardDto;
//import com.example.board.dto.BoardFileDto;
//import com.example.board.dto.BoardInsertRequest;
//import com.example.board.dto.BoardListResponse;
//import com.example.board.service.BoardService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.util.ObjectUtils;
//
//import java.net.URLEncoder;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@RestController
////@CrossOrigin(origins = "http://localhost:5173")
//@RequestMapping("/api")
//public class RestApiBoardController {
//    @Autowired
//    private BoardService boardService;
//
//    // 게시글 목록 조회
//    @Operation(summary = "게시판 목록 조회", description = "등록된 게시물 목록을 조회해서 반환합니다.")
//    @GetMapping("/board")
//    public List<BoardListResponse> openBoardList() throws Exception {
//
//        List<BoardDto> boardList = boardService.selectBoardList();
//
//        /*List<BoardListResponse> results를 만들어서
//        BoardDto 리스트를 BoardListResponse 리스트로 변환하여 반환하는 과정.*/
//        List<BoardListResponse> results = new ArrayList<>();
//        /*
//          너무 번거롭다!!!
//        for (BoardDto dto : boardList) {
//            BoardListResponse res = new BoardListResponse();
//            res.setBoardIdx(dto.getBoardIdx());
//            res.setTitle(dto.getTitle());
//            res.setHitCnt(dto.getHitCnt());
//            res.setCreatedDt(dto.getCreatedDt());
//
//            <ModelMapper를 이용>
//            BoardListResponse res = new ModelMapper().map(dto, BoardListResponse.class);
//            results.add(res);
//        }
//        */
//        // 람다식으로 더 간결하게 표현
//        boardList.forEach(dto -> results.add(new ModelMapper().map(dto, BoardListResponse.class)));
//        return results;
//    }
//
//
//    // 글쓰기 화면 불러오기
//    @GetMapping("/board/write")
//    public String openBoardWrite() throws Exception {
//        return "board/restBoardWrite";
//    }
//
//    // 게시글 등록
//    @PostMapping(value = "/board", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void insertBoard(@RequestParam("board") String boardData, MultipartHttpServletRequest request) throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        BoardDto boardDto = objectMapper.readValue(boardData, BoardDto.class);
//        boardService.insertBoard(boardDto, request);
//    }
//
//
//    // 게시글 상세 조회
//    @Operation(summary = "게시판 상세 조회", description = "게시물 아이디와 일치하는 게시물의 상세 정보를 조회해서 반환합니다.")
//    @Parameter(name = "boardIdx", description = "게시물 아이디", required = true)
//    @GetMapping("/board/{boardIdx}")
//    public ResponseEntity<Object> openBoardDetail(@PathVariable("boardIdx") int boardIdx)
//            throws Exception{
//        BoardDto boardDto = boardService.selectBoardDetail(boardIdx);
//        if (boardDto == null) {
//            // message를 전달할 객체
//            // -> Dto를 만들어도 되지만 보통 임시적으로 사용하는 것들은 보통 Map을 많이 사용
//            Map<String,Object> result = new HashMap<>();
//            result.put("code", HttpStatus.NOT_FOUND.value());
//            result.put("name", HttpStatus.NOT_FOUND.value());
//            result.put("message", "게시판 번호 " + boardIdx + "와 일치하는 게시물이 존재하지 않습니다.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
//        } else {
//            return ResponseEntity.status(HttpStatus.OK).body(boardDto);
//
//        }
//    }
//
//    // 게시글 수정
//    @PutMapping("/board/{boardIdx}")
//    public void updateboard(@PathVariable("boardIdx")int boardIdx, @RequestBody BoardDto boardDto)
//            throws Exception {
//        boardDto.setBoardIdx(boardIdx);
//        boardService.updateBoard(boardDto);
//    }
//
//    // 게시글 삭제
//    @DeleteMapping("/board/{boardIdx}")
//    public void deleteBoard (@PathVariable int boardIdx) throws Exception {
//        boardService.deleteBoard(boardIdx);
//    }
//
//    @GetMapping("/board/file")
//    public void downloadBoardFile(@RequestParam("idx") int idx, @RequestParam("boardIdx") int boardIdx, HttpServletResponse response) throws Exception {
//        BoardFileDto boardFileDto = boardService.selectBoardFileInfo(idx, boardIdx);
//        if (ObjectUtils.isEmpty(boardFileDto)) {
//            return;
//        }
//
//        Path path = Paths.get(boardFileDto.getStoredFilePath());
//        byte[] file = Files.readAllBytes(path);
//
//        response.setContentType("application/octet-stream");
//        response.setContentLength(file.length);
//        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(boardFileDto.getOriginalFileName(), "UTF-8") + "\";");
//        response.setHeader("Content-Transfer-Encoding", "binary");
//        response.getOutputStream().write(file);
//        response.getOutputStream().flush();
//        response.getOutputStream().close();
//    }
//}
//
//
//
//
