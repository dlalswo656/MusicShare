package MusicShare.MusicShare.user.repository;

import MusicShare.MusicShare.user.entity.ShareReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShareReplyRepository extends JpaRepository<ShareReplyEntity, Long> {
    List<ShareReplyEntity> findByShareId(Long boardShareId);
}
