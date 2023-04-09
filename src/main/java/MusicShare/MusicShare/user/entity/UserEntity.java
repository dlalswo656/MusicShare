package MusicShare.MusicShare.user.entity;

import MusicShare.MusicShare.user.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "ms_user")
public class UserEntity {

    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(unique = true) // unique 제약조건 추가
    private String email;

    @Column
    private  String password;

    @Column
    private String name;

    @Column
    private  String phone;

    @Column
    private String role; // 유저, 관리자 역할 추가

    // 디비 값이 이상하게 들어오거나 에러가 뜨면 이 부분일 가능성 크다
    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setName(userDTO.getName());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setRole(userDTO.getRole());

        return userEntity;
    }

    // 스프링 jpa DB에서 업데이트 쿼리가 정상 작동
    public static UserEntity toUpdateUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setName(userDTO.getName());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setRole(userDTO.getRole());

        return userEntity;
    }
}
