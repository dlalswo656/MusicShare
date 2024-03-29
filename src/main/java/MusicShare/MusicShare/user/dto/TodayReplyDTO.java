package MusicShare.MusicShare.user.dto;

import MusicShare.MusicShare.user.entity.TodayReplyEntity;
import lombok.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class TodayReplyDTO {
    private Long id; // 댓글 번호
    private Long userId; // 작성자 아이디
    private String userName; // 작성자 이름
    private Long boardTodayId; // 게시글 번호
    private String replyContent; // 댓글 내용
    private LocalDateTime replyCreatedTime;
    private LocalDateTime replyUpdatedTime;

    public TodayReplyDTO(TodayReplyEntity updatedReply) {
        // 기본 생성자
    }

    public static TodayReplyDTO toTodayReplyDTO(TodayReplyEntity todayReplyEntity) {
        TodayReplyDTO todayReplyDTO = new TodayReplyDTO();
        todayReplyDTO.setId(todayReplyEntity.getId());
        todayReplyDTO.setReplyContent(todayReplyEntity.getReplyContent());
        todayReplyDTO.setReplyCreatedTime(todayReplyEntity.getCreatedTime());
//        todayReplyDTO.setReplyUpdatedTime(todayReplyEntity.getUpdatedTime());


        // 작성자 id, name 가져오기
        if (todayReplyEntity.getUser() != null) {
            todayReplyDTO.setUserId(todayReplyEntity.getUser().getId());
            todayReplyDTO.setUserName(todayReplyEntity.getUser().getName());
        }

        if (todayReplyEntity.getToday() != null) {
            todayReplyDTO.setBoardTodayId(todayReplyEntity.getToday().getId());
        }

        return todayReplyDTO;
    }

}
