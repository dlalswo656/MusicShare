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

    @Column
    private String todayWriter;

    @Column(length = 500)
    private String todayContent;

    @Column
    private int todayHits;


    public static BoardTodayEntity toBoardTodayEntity(BoardTodayDTO boardTodayDTO) {
        BoardTodayEntity boardTodayEntity = new BoardTodayEntity();
        boardTodayEntity.setTodayTitle(boardTodayDTO.getTodayTitle());
        boardTodayEntity.setTodayContent(boardTodayDTO.getTodayContent());
        boardTodayEntity.setTodayWriter(boardTodayDTO.getTodayWriter());
        boardTodayEntity.setTodayHits(0);

        return boardTodayEntity;
    }

    public static BoardTodayEntity toUpdateTodayEntity(BoardTodayDTO boardTodayDTO) {
        BoardTodayEntity boardTodayEntity = new BoardTodayEntity();
        boardTodayEntity.setId(boardTodayDTO.getId());
        boardTodayEntity.setTodayTitle(boardTodayDTO.getTodayTitle());
        boardTodayEntity.setTodayContent(boardTodayDTO.getTodayContent());
        boardTodayEntity.setTodayWriter(boardTodayDTO.getTodayWriter());
        boardTodayEntity.setTodayHits(boardTodayDTO.getTodayHits());
        return boardTodayEntity;
    }

}
