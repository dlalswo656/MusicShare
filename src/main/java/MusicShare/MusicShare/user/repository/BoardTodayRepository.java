package MusicShare.MusicShare.user.repository;

import MusicShare.MusicShare.user.entity.BoardTodayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface BoardTodayRepository extends JpaRepository<BoardTodayEntity, Long> {
    // Update board_today set today_hits = today_hits+1 where id=?

    @Modifying
    @Query(value = "Update BoardTodayEntity b set b.todayHits = b.todayHits + 1 where b.id = :id")
    void UpdateHits(@Param("id") Long id);
}
