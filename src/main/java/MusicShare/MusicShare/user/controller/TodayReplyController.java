package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.TodayReplyDTO;
import MusicShare.MusicShare.user.service.TodayReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class TodayReplyController {

    private final TodayReplyService todayReplyService;

    @PostMapping("/Board/Today/{boardTodayId}/Reply")
    public String TodayReplySave(@RequestBody TodayReplyDTO todayReplyDTO) {
        Long boardTodayId = todayReplyService.saveTodayReply(todayReplyDTO);
        System.out.println("제발 에러 늪에서 빠져 나오자 : " + boardTodayId);
        return "redirect:/Board/Today/" + boardTodayId;
    }
}
