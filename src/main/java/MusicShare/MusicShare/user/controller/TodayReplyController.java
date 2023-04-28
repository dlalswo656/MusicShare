package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.TodayReplyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Today")
public class TodayReplyController {

    @PostMapping("/Reply")
    public @ResponseBody String ReplySave(@ModelAttribute TodayReplyDTO todayReplyDTO) {
        System.out.println("TodayReplyDTO = " + todayReplyDTO);
        return "요청 Nice";
    }
}
