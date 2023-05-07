package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.TodayReplyDTO;
import MusicShare.MusicShare.user.service.TodayReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 댓글 더보기 ajax 처리
    @GetMapping("/Board/Today/{boardTodayId}/Reply")
    @ResponseBody
    public List<TodayReplyDTO> getTodayReplies(@PathVariable Long boardTodayId) {
        return todayReplyService.getTodayByBoardTodayId(boardTodayId);
    }

    // 댓글 수정
    @PutMapping("/Board/Today/{boardTodayId}/Reply/{replyId}")
    @ResponseBody
    public void updateTodayReply(@PathVariable Long boardTodayId, @PathVariable("replyId") Long replyId, @RequestBody TodayReplyDTO todayReplyDTO) {
        todayReplyDTO.setId(replyId);
        todayReplyDTO.setBoardTodayId(boardTodayId);
        todayReplyService.updateTodayReply(todayReplyDTO);

        // 디버깅
        System.out.println(" 야 : " + replyId);
        System.out.println("잘 가져 : " + boardTodayId);
        System.out.println("오고 있니 ? : " + todayReplyDTO);
    }

    // 댓글 삭제
    @DeleteMapping("/Board/Today/{boardTodayId}/Reply/{replyId}")
    @ResponseBody
    public void deleteTodayReply(@PathVariable Long boardTodayId, @PathVariable("replyId") Long replyId) {
        todayReplyService.deleteTodayReply(replyId);
    }

}
