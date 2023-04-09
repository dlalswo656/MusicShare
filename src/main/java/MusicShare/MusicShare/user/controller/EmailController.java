package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.service.EmailService;
import MusicShare.MusicShare.user.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@Repository
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email, HttpSession session) {


        // 비밀번호 분실 처리 로직
        emailService.sendForgotPasswordEmail(email);

        return ResponseEntity.ok("임시 비밀번호를 이메일로 발송했습니다.");
    }
}

