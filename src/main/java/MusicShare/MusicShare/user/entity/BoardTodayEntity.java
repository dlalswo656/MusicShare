package MusicShare.MusicShare.user.entity;

import MusicShare.MusicShare.user.dto.BoardTodayDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private String todayContent;

    @Column(length = 20, nullable = false) // 크기 20, not null
    private String todayWriter;

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

}
