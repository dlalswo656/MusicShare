package MusicShare.MusicShare.user.entity;

import MusicShare.MusicShare.user.dto.MusicShareDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board_share")
public class MusicShareEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String shareTitle;

    @Column(length = 500)
    private String shareContent;

    @Column
    private int shareHits;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "share") // ShareReplyEntity에서 JoinColumn
    private List<ShareReplyEntity> replyList = new ArrayList<>();

    public static MusicShareEntity toMusicShareEntity(MusicShareDTO musicShareDTO, UserEntity user) {
        MusicShareEntity musicShareEntity = new MusicShareEntity();
        musicShareEntity.setShareTitle(musicShareDTO.getShareTitle());
        musicShareEntity.setShareContent(musicShareDTO.getShareContent());
        musicShareEntity.setShareHits(0);
        musicShareEntity.setUser(user);

        return musicShareEntity;
    }

    public static MusicShareEntity toUpdateShareEntity(MusicShareDTO musicShareDTO) {
        MusicShareEntity musicShareEntity = new MusicShareEntity();
        musicShareEntity.setId(musicShareDTO.getId());
        musicShareEntity.setShareTitle(musicShareDTO.getShareTitle());
        musicShareEntity.setShareContent(musicShareDTO.getShareContent());
        musicShareEntity.setShareHits(musicShareDTO.getShareHits());
        return musicShareEntity;
    }
}
