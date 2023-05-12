package MusicShare.MusicShare.user.dto;

import MusicShare.MusicShare.user.entity.MusicShareEntity;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class MusicShareDTO {
    private Long id;
    private String shareTitle;
    private Long userId;
    private String userName;
    private String shareContent;
    private int shareHits;
    private LocalDateTime shareCreatedTime;
    private LocalDateTime shareUpdateTime;

    public static MusicShareDTO toMusicShareDTO(MusicShareEntity musicShareEntity) {
        MusicShareDTO musicShareDTO = new MusicShareDTO();
        musicShareDTO.setId(musicShareEntity.getId());
        musicShareDTO.setShareTitle(musicShareEntity.getShareTitle());
        musicShareDTO.setShareContent(musicShareEntity.getShareContent());
        musicShareDTO.setShareHits(musicShareEntity.getShareHits());
        musicShareDTO.setShareCreatedTime(musicShareEntity.getCreatedTime());
        musicShareDTO.setShareUpdateTime(musicShareEntity.getUpdatedTime());
        musicShareDTO.setUserName(musicShareEntity.getUser().getName());

        // 작성자 이름
        if (musicShareEntity.getUser() != null) {
            musicShareDTO.setUserId(musicShareEntity.getUser().getId());
            musicShareDTO.setUserName(musicShareEntity.getUser().getName());
        }

        return musicShareDTO;
    }
}
