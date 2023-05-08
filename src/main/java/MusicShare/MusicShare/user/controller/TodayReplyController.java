package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.TodayReplyDTO;
import MusicShare.MusicShare.user.entity.TodayReplyEntity;
import MusicShare.MusicShare.user.service.TodayReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Long> toUpdateTodayReply(@PathVariable Long boardTodayId, @PathVariable Long replyId, @RequestBody TodayReplyDTO todayReplyDTO) {
        todayReplyService.toUpdateTodayReply(replyId, todayReplyDTO);

        System.out.println("댓글 아이디 : " + replyId);
        System.out.println("댓글 디티오 : " + todayReplyDTO);

        return ResponseEntity.ok(replyId);
    }

    // 댓글 삭제
    @DeleteMapping("/Board/Today/{boardTodayId}/Reply/{replyId}")
    public ResponseEntity toDeleteTodayReply(@PathVariable Long id) {
        todayReplyService.delete(id);
        return ResponseEntity.ok(id);
    }

}
