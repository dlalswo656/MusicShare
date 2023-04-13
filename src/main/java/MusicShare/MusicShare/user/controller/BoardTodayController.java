package MusicShare.MusicShare.user.controller;

//import MusicShare.MusicShare.user.repository.BoardTodayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Board")
public class BoardTodayController {

//    private final BoardTodayRepository boardTodayRepository;

    // 오늘의 음악
    @GetMapping("/Today")
    public String TodayBoard() {
        return "board/Today";
    }

}
