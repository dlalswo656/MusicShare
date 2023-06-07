package MusicShare.MusicShare.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class NoticeController {
    
    // 공지사항
    @GetMapping("/Notice")
    public String Notice() {

        return "admin/Notice";
    }
}
