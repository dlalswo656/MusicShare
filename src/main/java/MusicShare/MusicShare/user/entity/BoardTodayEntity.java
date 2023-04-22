package MusicShare.MusicShare.user.entity;

import MusicShare.MusicShare.user.dto.BoardTodayDTO;
import MusicShare.MusicShare.user.service.UserService;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board_today")
public class BoardTodayEntity extends BaseEntity {

    @Id // pk 컬럼 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String todayTitle;

    @Column(length = 500)
    private String todayContent;

    @Column
    private int todayHits;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "user")
    private List<BoardTodayEntity> boards;

    public static BoardTodayEntity toBoardTodayEntity(BoardTodayDTO boardTodayDTO, UserEntity user) {
        BoardTodayEntity boardTodayEntity = new BoardTodayEntity();
        boardTodayEntity.setId(boardTodayDTO.getId());
        boardTodayEntity.setTodayTitle(boardTodayDTO.getTodayTitle());
        boardTodayEntity.setTodayContent(boardTodayDTO.getTodayContent());
        boardTodayEntity.setTodayHits(0);
        boardTodayEntity.setUser(user);

        return boardTodayEntity;
    }

    public static BoardTodayEntity toUpdateTodayEntity(BoardTodayDTO boardTodayDTO, UserEntity user) {
        BoardTodayEntity boardTodayEntity = new BoardTodayEntity();
        boardTodayEntity.setId(boardTodayDTO.getId());
        boardTodayEntity.setTodayTitle(boardTodayDTO.getTodayTitle());
        boardTodayEntity.setTodayContent(boardTodayDTO.getTodayContent());
        boardTodayEntity.setTodayHits(boardTodayDTO.getTodayHits());
        boardTodayEntity.setUser(user);
        return boardTodayEntity;
    }

    public String getName() {
        return user != null ? user.getName() : "";
    }
}
