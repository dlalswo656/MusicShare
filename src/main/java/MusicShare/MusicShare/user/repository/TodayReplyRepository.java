package MusicShare.MusicShare.user.repository;

import MusicShare.MusicShare.user.entity.TodayReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodayReplyRepository extends JpaRepository<TodayReplyEntity, Long> {
    List<TodayReplyEntity> findByTodayId(Long boardTodayId);
}
