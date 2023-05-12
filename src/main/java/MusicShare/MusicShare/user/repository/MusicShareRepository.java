package MusicShare.MusicShare.user.repository;

import MusicShare.MusicShare.user.entity.MusicShareEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MusicShareRepository extends JpaRepository<MusicShareEntity, Long> {

    @Modifying
    @Query(value = "Update MusicShareEntity b set b.shareHits = b.shareHits + 1 where b.id = :id")
    void UpdateHits(@Param("id") Long id);
}
