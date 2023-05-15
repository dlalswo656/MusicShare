package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.TodayReplyDTO;
import MusicShare.MusicShare.user.repository.TodayReplyRepository;
import MusicShare.MusicShare.user.service.TodayReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TodayReplyController {

    private final TodayReplyService todayReplyService;
    private final TodayReplyRepository todayReplyRepository;

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
    public ResponseEntity<TodayReplyDTO> toUpdateTodayReply(@PathVariable Long boardTodayId, @PathVariable Long replyId, @RequestBody TodayReplyDTO todayReplyDTO) {
        TodayReplyDTO updatedReply = todayReplyService.toUpdateTodayReply(replyId, todayReplyDTO);

        return ResponseEntity.ok(updatedReply);
    }

    // 댓글 삭제
    @DeleteMapping("/Board/Today/{boardTodayId}/Reply/{replyId}")
    public ResponseEntity toDeleteTodayReply(@PathVariable Long boardTodayId, @PathVariable Long replyId) {
        todayReplyService.delete(replyId);
        return ResponseEntity.ok(replyId);
    }

}
