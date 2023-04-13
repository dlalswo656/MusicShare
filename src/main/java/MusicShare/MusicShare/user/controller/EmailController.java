package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.entity.UserEntity;
import MusicShare.MusicShare.user.repository.UserRepository;
import MusicShare.MusicShare.user.service.EmailService;
import MusicShare.MusicShare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@Repository
@RequiredArgsConstructor
public class EmailController {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserService userService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email, HttpSession session) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();

            // 비밀번호 분실 처리 로직
            String newPassword = emailService.sendForgotPasswordEmail(email);

            // 임시 비밀번호로 업데이트
            user.setPassword(newPassword);
            userRepository.save(user);

            return ResponseEntity.ok("임시 비밀번호를 이메일로 발송했습니다.");
        } else {
            // 이메일로 검색된 유저가 없는 경우
            return ResponseEntity.badRequest().body("해당 이메일을 가진 유저가 없습니다.");
        }
    }
}

