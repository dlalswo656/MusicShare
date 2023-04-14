package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.BoardTodayDTO;
import MusicShare.MusicShare.user.repository.BoardTodayRepository;
import MusicShare.MusicShare.user.service.BoardTodayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Board")
public class BoardTodayController {

    private final BoardTodayRepository boardTodayRepository;
    private final BoardTodayService boardTodayService;
    // 오늘의 음악
    @GetMapping("/Today")
    public String TodayBoard() {
        return "board/Today";
    }

    // 글작성
    @GetMapping("/TodayWrite")
    public String WriteForm() {
        return "board/TodayWrite";
    }

    @PostMapping("/TodayWrite")
    public String Save(@ModelAttribute BoardTodayDTO boardTodayDTO) {
        System.out.println("BoardTodayDTO : " + boardTodayDTO);
        boardTodayService.Save(boardTodayDTO);
        return "redirect:/Today";
    }
}
