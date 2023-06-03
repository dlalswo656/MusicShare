package MusicShare.MusicShare.user.entity;

import MusicShare.MusicShare.user.dto.ShareReplyDTO;
import MusicShare.MusicShare.user.repository.MusicShareRepository;
import MusicShare.MusicShare.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "share_reply")
public class ShareReplyEntity extends BaseEntity{

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String replyContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private MusicShareEntity share;

    public static ShareReplyEntity toShareReplyEntity(ShareReplyDTO shareReplyDTO, UserRepository userRepository,
                                                      MusicShareRepository musicShareRepository) {
        ShareReplyEntity shareReplyEntity = new ShareReplyEntity();
        shareReplyEntity.setId(shareReplyDTO.getId());
        shareReplyEntity.setReplyContent(shareReplyDTO.getReplyContent());

        if (shareReplyDTO.getUserId() != null) {
            Optional<UserEntity> userOpt = userRepository.findById(shareReplyDTO.getId());
            userOpt.ifPresent(shareReplyEntity::setUser);
        }

        if (shareReplyDTO.getBoardShareId() != null) {
            Optional<MusicShareEntity> musicShareOpt = musicShareRepository.findById(shareReplyDTO.getBoardShareId());
            musicShareOpt.ifPresent(shareReplyEntity::setShare);
        }

        return shareReplyEntity;
    }

}
