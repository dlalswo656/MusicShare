package MusicShare.MusicShare.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TodayReplyDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String todayContents;
    private Long TodayId;
    private LocalDateTime todayReplyTime;
}
