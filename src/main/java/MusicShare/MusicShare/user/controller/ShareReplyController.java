package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.ShareReplyDTO;
import MusicShare.MusicShare.user.repository.ShareReplyRepository;
import MusicShare.MusicShare.user.service.ShareReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShareReplyController {

    private final ShareReplyService shareReplyService;
    private final ShareReplyRepository shareReplyRepository;

    // 댓글 ajax 처리
    @PostMapping("/Music/Share/{boardShareId}/Reply")
    public String ShareReplySave(@PathVariable Long boardShareId, @RequestBody ShareReplyDTO shareReplyDTO) {
        shareReplyDTO.setBoardShareId(boardShareId);
        shareReplyService.saveShareReply(shareReplyDTO);

        System.out.println("음악 공유 댓글 " + shareReplyDTO);
        return "redirect:/Music/Share/" + boardShareId;
    }

    // 댓글 더보기 ajax 처리
    @GetMapping("/Music/Share/{boardShareId}/Reply")
    @ResponseBody
    public List<ShareReplyDTO> getShareReplies(@PathVariable Long boardShareId) {
        return shareReplyService.getShareByBoardShareId(boardShareId);
    }

    // 댓글 수정
    @PutMapping("/Music/Share/{boardShareId}/Reply/{replyId}")
    public ResponseEntity<ShareReplyDTO> toUpdateShareReply(@PathVariable Long boardShareId, @PathVariable Long replyId, @RequestBody ShareReplyDTO shareReplyDTO) {
        ShareReplyDTO updatedReply = shareReplyService.toUpdateShareReply(replyId, shareReplyDTO);

        return ResponseEntity.ok(updatedReply);
    }

    // 댓글 삭제
    @DeleteMapping("/Music/Share/{boardShareId}/Reply/{replyId}")
    public ResponseEntity toDeleteShareReply(@PathVariable Long boardShareId, @PathVariable Long replyId) {
        shareReplyService.delete(replyId);
        return ResponseEntity.ok(replyId);
    }
}
