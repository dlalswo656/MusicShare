package MusicShare.MusicShare.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 배개변수로 하는 생성자
public class BoardTodayDTO {
    private Long id;
    private String todayTitle;
    private String todayWriter;
    private int todayHits;
    private LocalDateTime todayCreatedTime;

}
