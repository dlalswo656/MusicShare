package MusicShare.MusicShare.user.service;


import MusicShare.MusicShare.user.config.SecurityConfig;
import MusicShare.MusicShare.user.dto.UserDTO;
import MusicShare.MusicShare.user.entity.UserEntity;
import MusicShare.MusicShare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    public void Save(UserDTO userDTO) {
        
        // 비밀번호 암호화
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);

        // 1. dto -> entity 변환
        // 2. repository의 save 메서드 호출
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        userRepository.save(userEntity);
        // repository의 save 메서드 호출 (조건. entity 객체를 넘겨줘야 함)

    }

    public UserDTO Login(UserDTO userDTO) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는 지 판단
        */
        Optional<UserEntity> byEmail = userRepository.findByEmail(userDTO.getEmail());
        if (byEmail.isPresent()) {
            // 조회 결과 있음(해당 이메일을 가진 회원 정보가 있다)
            UserEntity userEntity = byEmail.get();
            if (userEntity.getPassword().equals(userDTO.getPassword())) {
                // 비밀번호 일치
                // entity -> dto 변환 후 리턴
                UserDTO dto = UserDTO.toUserDTO(userEntity);
                return dto;
            } else {
                // 비밀번호 불일치(로그인 실패)
                return null;
            }
        } else {
            // 조회 결과 없음(해당 이메일을 가진 회원이 없다)
            return null;
        }
    }

    // 관리자 페이지의 회원 리스트
    public List<UserDTO> findAll() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (UserEntity userEntity: userEntityList) {
            userDTOList.add(UserDTO.toUserDTO(userEntity));

        }
        return userDTOList;
    }

    public UserDTO findById(Long id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
//            UserEntity userEntity = optionalUserEntity.get();
//            UserDTO userDTO = UserDTO.toUserDTO(userEntity);
//            return userDTO;
//            이 3줄을 아래 1줄로 표현
            return UserDTO.toUserDTO(optionalUserEntity.get());
        } else {
            return null;
        }
    }

    // 업데이트 이메일로 DB 회원정보 조회
    public UserDTO UpdateForm(String myEmail) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(myEmail);
        if (optionalUserEntity.isPresent()) {
            return UserDTO.toUserDTO(optionalUserEntity.get());
        } else {
            return null;
        }
    }

    // 회원가입 엔티티를 가져오게 되면 그냥 update가 안 되고 insert 됨
    public void Update(UserDTO userDTO) {

        // 내정보 수정 비밀번호 암호화
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);

        userRepository.save(UserEntity.toUpdateUserEntity(userDTO));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public String emailCheck(String email) {
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            // 조회 결과가 있다 -> 사용할 수 없다.
            return null;
        } else {
            // 조회 결과가 없다 -> 사용할 수 있다.
            return "ok";
        }
    }


}