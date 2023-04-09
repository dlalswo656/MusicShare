package MusicShare.MusicShare.user.dto;

import MusicShare.MusicShare.user.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String role; // 유저, 관리자 추가

    public static UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setName(userEntity.getName());
        userDTO.setPhone(userEntity.getPhone());
        userDTO.setRole(userEntity.getRole()); // 유저, 관리자 추가
        
        return userDTO;

    }


}
