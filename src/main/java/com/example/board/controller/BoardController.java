//package com.example.board.controller;
//
//import com.example.board.dto.BoardDto;
//import com.example.board.dto.BoardFileDto;
//import com.example.board.service.BoardService;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.domain.JpaSort;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.net.URLEncoder;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//@Controller
//public class BoardController {
//    @Autowired
//    private BoardService boardService;
//
//    // 게시글 목록 조회 요청 처리 메서드
//    @GetMapping("/board/openBoardList.do")
//    // ModelAndView : 어떤 데이터를 지정된 템플릿에 적용해서 그 결과를 반환해줌
//    public ModelAndView openBoardList() throws Exception {
//        // board/boardList 템플릿에 내가 가지고 있는 데이터를 적용해 리턴할 것이다
//        ModelAndView mw = new ModelAndView("board/boardList");
//
//        // 데이터는 서비스의 메소드로 가져올 것이다
//        // BoardDto 타입의 데이터가 들어있는 리스트를 반환받아 list 변수에 저장
//        List<BoardDto> list = boardService.selectBoardList(); // db에서 조회해온 결과
//        mw.addObject("list", list); // 넘어온 리스트 값을 템플릿(view)에 적용
//        return mw;
//    }
//
//    // 글쓰기 버튼 클릭 시 등록 템플릿으로 이동
//    @GetMapping("/board/openBoardWrite.do")
//    public String openBoardWrite() throws Exception {
//        return "board/boardWrite"; //보여줄 페이지(템플릿)
//    }
//
//    // 게시글 등록 요청 처리 메서드
//    @PostMapping("/board/insertBoard.do")
//    public String insertBoard(BoardDto boardDto, MultipartHttpServletRequest request) throws Exception {
//        boardService.insertBoard(boardDto,request);
//        return "redirect:/board/openBoardList.do"; //목록조회로 리다이렉션
//    }
//
//    // 게시글 상세 조회 요청 처리 메서드
//    @GetMapping("/board/openBoardDetail.do")
//    public ModelAndView openBoardDetail(@RequestParam("boardIdx") int boardIdx) throws Exception {
//        BoardDto boardDto = boardService.selectBoardDetail(boardIdx);
//
//        ModelAndView mw = new ModelAndView("/board/boardDetail");
//        mw.addObject("board", boardDto);
//        return mw;
//    }
//
//    // 게시글 수정 요청 처리 메서드
//    @PostMapping("/board/updateBoard.do")
//    public String updateBoard(BoardDto boardDto) throws Exception {
//        boardService.updateBoard(boardDto);
//        return "redirect:/board/openBoardList.do";
//    }
//
//    // 게시글 삭제 요청 처리 메서드
//    @PostMapping("/board/deleteBoard.do")
//    public String deleteBoard(@RequestParam("boardIdx") int boardIdx) throws Exception {
//        boardService.deleteBoard(boardIdx);
//        return "redirect:/board/openBoardList.do";
//    }
//
//    // 파일 다운로드 요청을 처리하는 메서드
//    @GetMapping("/board/downloadBoardFile.do")
//    public void downloadBoardFile(@RequestParam("idx") int idx, @RequestParam("boardIdx") int boardIdx, HttpServletResponse response) throws Exception {
//        // idx와 boardIdx가 일치하는 파일 정보를 조회
//        BoardFileDto boardFileDto = boardService.selectBoardFileInfo(idx, boardIdx);
//        if (ObjectUtils.isEmpty(boardFileDto)) {
//            return;
//        }
//
//        // 원본 파일 저장 위치에서 파일을 읽어서 호출(요청)한 곳으로 파일을 응답으로 전달
//        Path path = Paths.get(boardFileDto.getStoredFilePath());
//        byte[] file = Files.readAllBytes(path);
//
//        response.setContentType("application/octet-stream");	//⇐ 브라우저가 해당 콘텐츠를 바이너리 파일로 처리하도록 지정
//        response.setContentLength(file.length);		//⇐ 다운로드할 파일의 크기를 명시
//        response.setHeader("Content-Disposition", 	//	⇐ 브라우저가 파일을 다운로드하도록 지시
//                "attachment; fileName=\"" + URLEncoder.encode(boardFileDto.getOriginalFileName(), "UTF-8") + "\";");
//        response.setHeader("Content-Transfer-Encoding", "binary");	// ⇐ 응답 본문을 바이너리로 전송하도록 지정
//        response.getOutputStream().write(file);
//        response.getOutputStream().flush();
//        response.getOutputStream().close();
//    }
//
//
//
//}
