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

    // 댓글 ajax 처리
    @PostMapping("/Board/Today/{boardTodayId}/Reply")
    public String TodayReplySave(@PathVariable Long boardTodayId, @RequestBody TodayReplyDTO todayReplyDTO) {
        todayReplyDTO.setBoardTodayId(boardTodayId);
        todayReplyService.saveTodayReply(todayReplyDTO);

        return "redirect:/Board/Today/" + boardTodayId;
    }
}
