package MusicShare.MusicShare.user.repository;

import MusicShare.MusicShare.user.entity.BoardNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardNoticeRepository extends JpaRepository<BoardNoticeEntity, Long> {

    @Modifying
    @Query(value = "Update BoardNoticeEntity b set b.noticeHits = b.noticeHits + 1 where b.id = :id")
    void UpdateHits(@Param("id") Long id);

}
