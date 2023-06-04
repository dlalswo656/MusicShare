package MusicShare.MusicShare.user.dto;

import MusicShare.MusicShare.user.entity.ShareReplyEntity;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class ShareReplyDTO {
    private Long id; // 댓글 번호
    private Long userId; // 작성자 아이디
    private String userName; // 작성자 이름
    private Long boardShareId; // 게시글 번호
    private String replyContent; // 댓글 내용
    private LocalDateTime replyCreatedTime;
    private LocalDateTime replyUpdatedTime;

    public ShareReplyDTO(ShareReplyEntity updatedReply) {
        // 기본 생성자
    }

    public static ShareReplyDTO toShareReplyDTO(ShareReplyEntity shareReplyEntity) {
        ShareReplyDTO shareReplyDTO = new ShareReplyDTO();
        shareReplyDTO.setId(shareReplyEntity.getId());
        shareReplyDTO.setReplyContent(shareReplyEntity.getReplyContent());
        shareReplyDTO.setReplyCreatedTime(shareReplyEntity.getCreatedTime());
//        todayReplyDTO.setReplyUpdatedTime(todayReplyEntity.getUpdatedTime());


        // 작성자 id, name 가져오기
        if (shareReplyEntity.getUser() != null) {
            shareReplyDTO.setUserId(shareReplyEntity.getUser().getId());
            shareReplyDTO.setUserName(shareReplyEntity.getUser().getName());
        }

        if (shareReplyEntity.getShare() != null) {
            shareReplyDTO.setBoardShareId(shareReplyEntity.getShare().getId());
        }

        return shareReplyDTO;
    }
}
