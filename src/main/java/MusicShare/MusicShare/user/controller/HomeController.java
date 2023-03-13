package MusicShare.MusicShare.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // 기본페이지 요청 메서드
    @GetMapping("/")
    public String index() {
        return "index"; // templates 폴더의 index.html을 찾아감
    }

}

