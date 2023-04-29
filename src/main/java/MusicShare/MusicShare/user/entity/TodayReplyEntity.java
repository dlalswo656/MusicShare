package MusicShare.MusicShare.user.entity;

import MusicShare.MusicShare.user.dto.BoardTodayDTO;
import MusicShare.MusicShare.user.dto.TodayReplyDTO;
import MusicShare.MusicShare.user.repository.BoardTodayRepository;
import MusicShare.MusicShare.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "today_reply")
public class TodayReplyEntity {

    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String replyContent;

    @Column
    private LocalDateTime ReplyCreatedTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardTodayEntity today;

    public static TodayReplyEntity toTodayReplyEntity(TodayReplyDTO todayReplyDTO, UserRepository userRepository,
    BoardTodayRepository boardTodayRepository) {
        TodayReplyEntity todayReplyEntity = new TodayReplyEntity();
        todayReplyEntity.setId(todayReplyDTO.getId());
        todayReplyEntity.setReplyContent(todayReplyDTO.getTodayContents());
        todayReplyEntity.setReplyCreatedTime(todayReplyDTO.getReplyCreatedTime());

        if (todayReplyDTO.getUserId() != null) {
            Optional<UserEntity> userOpt = userRepository.findById(todayReplyDTO.getUserId());
            userOpt.ifPresent(todayReplyEntity::setUser);
        }

        if (todayReplyDTO.getBoardTodayId() != null) {
            Optional<BoardTodayEntity> boardTodayOpt = boardTodayRepository.findById(todayReplyDTO.getBoardTodayId());
            boardTodayOpt.ifPresent(todayReplyEntity::setToday);
        }

        return todayReplyEntity;
    }

}
