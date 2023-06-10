package MusicShare.MusicShare.user.entity;

import MusicShare.MusicShare.user.dto.BoardNoticeDTO;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board_notice")
public class BoardNoticeEntity extends BaseEntity {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String noticeTitle;

    @Column(length = 500)
    private String noticeContent;

    @Column
    private int noticeHits;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public static BoardNoticeEntity toBoardNoticeEntity(BoardNoticeDTO boardNoticeDTO, UserEntity user) {
        BoardNoticeEntity boardNoticeEntity = new BoardNoticeEntity();
        boardNoticeEntity.setNoticeTitle(boardNoticeDTO.getNoticeTitle());
        boardNoticeEntity.setNoticeContent(boardNoticeDTO.getNoticeContent());
        boardNoticeEntity.setNoticeHits(0);
        boardNoticeEntity.setUser(user);

        return boardNoticeEntity;
    }

}
