package MusicShare.MusicShare.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board_notice")
public class NoticeEntity extends BaseEntity {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String noticeTitle;

    @Column(length = 500)
    private String noticeContent;

    @Column
    private int noticeHits;

}
