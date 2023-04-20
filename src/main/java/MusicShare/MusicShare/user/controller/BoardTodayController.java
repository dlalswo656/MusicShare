package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.BoardTodayDTO;
import MusicShare.MusicShare.user.repository.BoardTodayRepository;
import MusicShare.MusicShare.user.service.BoardTodayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Board")
public class BoardTodayController {

    private final BoardTodayRepository boardTodayRepository;
    private final BoardTodayService boardTodayService;

    // 오늘의 음악
    @GetMapping("/Today")
    public String TodayBoard(Model model, HttpSession session) {
//        String LoginEmail = (String) session.getAttribute("LoginEmail");
//        // 세션 이메일 값 가져와 모델 추가
//        model.addAttribute("LoginEmail", LoginEmail);

        // DB에 게시글 데이터를 가져옴
        List<BoardTodayDTO> boardTodayDTOList = boardTodayService.findAll();
        model.addAttribute("boardTodayList", boardTodayDTOList);
        return "board/Today";
    }

    // 글작성
    @GetMapping("/TodayWrite")
    public String TodayWrite() {

        return "board/TodayWrite";
    }

    @PostMapping("/TodayWrite")
    public String TodaySave(@ModelAttribute BoardTodayDTO boardTodayDTO, HttpSession session) {
        System.out.println("BoardTodayDTO : " + boardTodayDTO);
        boardTodayService.TodaySave(boardTodayDTO);
        return "redirect:/Board/Today";
    }

    // 게시글 정보
    @GetMapping("/Today/{id}")
    public String TodayId(@PathVariable Long id, Model model) {
        boardTodayService.TodayHits(id);
        BoardTodayDTO boardTodayDTO = boardTodayService.TodayId(id);
        model.addAttribute("boardToday", boardTodayDTO);
        return "board/TodayDetail";
    }

    // 게시글 수정
    @GetMapping("/Update")
    public String TodayUpdate(@PathVariable Long id, Model model) {
        BoardTodayDTO boardTodayDTO = boardTodayService.TodayId(id);
        model.addAttribute("todayUpdate", boardTodayDTO);
        return "board/TodayUpdate";
    }

}
