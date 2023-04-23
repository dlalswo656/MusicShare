package MusicShare.MusicShare.user.dto;

import MusicShare.MusicShare.user.entity.BoardTodayEntity;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 배개변수로 하는 생성자
public class BoardTodayDTO {
    private Long id; // 게시글 넘버
    private String todayTitle;
    private String todayContent;
    private String todayWriter;
    private int todayHits;
    private LocalDateTime todayCreatedTime;
    private LocalDateTime todayUpdatedTime;

    public static BoardTodayDTO toBoardTodayDTO(BoardTodayEntity boardTodayEntity) {
        BoardTodayDTO boardTodayDTO = new BoardTodayDTO();
        boardTodayDTO.setId(boardTodayEntity.getId());
        boardTodayDTO.setTodayTitle(boardTodayEntity.getTodayTitle());
        boardTodayDTO.setTodayWriter(boardTodayEntity.getTodayWriter());
        boardTodayDTO.setTodayContent(boardTodayEntity.getTodayContent());
        boardTodayDTO.setTodayHits(boardTodayEntity.getTodayHits());
        boardTodayDTO.setTodayCreatedTime(boardTodayEntity.getCreatedTime());
        boardTodayDTO.setTodayUpdatedTime(boardTodayEntity.getUpdatedTime());

        return boardTodayDTO;
    }

}
