package MusicShare.MusicShare.user.dto;

import MusicShare.MusicShare.user.entity.BoardNoticeEntity;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor
public class BoardNoticeDTO {
    private Long id;
    private String noticeTitle;
    private Long userId;
    private String userRole; // user와 admin
    private String userName;
    private String noticeContent;
    private int noticeHits;
    private LocalDateTime noticeCreatedTime;
    private LocalDateTime noticeUpdatedTime;

    public static BoardNoticeDTO toBoardNoticeDTO(BoardNoticeEntity boardNoticeEntity) {
        BoardNoticeDTO boardNoticeDTO = new BoardNoticeDTO();
        boardNoticeDTO.setId(boardNoticeEntity.getId());
        boardNoticeDTO.setNoticeTitle(boardNoticeEntity.getNoticeTitle());
        boardNoticeDTO.setNoticeContent(boardNoticeEntity.getNoticeContent());
        boardNoticeDTO.setNoticeHits(boardNoticeEntity.getNoticeHits());
        boardNoticeDTO.setNoticeCreatedTime(boardNoticeEntity.getCreatedTime());
        boardNoticeDTO.setNoticeUpdatedTime(boardNoticeEntity.getUpdatedTime());
        boardNoticeDTO.setUserRole(boardNoticeEntity.getUser().getRole());

        // 작성자 역할
        if (boardNoticeEntity.getUser() != null) {
            boardNoticeDTO.setUserId(boardNoticeEntity.getUser().getId());
            boardNoticeDTO.setUserRole(boardNoticeEntity.getUser().getRole());
            boardNoticeDTO.setUserName(boardNoticeEntity.getUser().getName());
        }

        return boardNoticeDTO;
    }

}
