package MusicShare.MusicShare.user.repository;

import MusicShare.MusicShare.user.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//                                                      클래스     pk 값
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // 이메일로 회원 정보 조회 (select * from "user(테이블명)" where "email(컬럼명)" = ?)
    Optional<UserEntity> findByEmail(String email);
}
