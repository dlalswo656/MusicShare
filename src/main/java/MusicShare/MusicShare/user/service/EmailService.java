package MusicShare.MusicShare.user.service;

import MusicShare.MusicShare.user.config.SecurityConfig;
import MusicShare.MusicShare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Random;

@Service
@Repository
@RequiredArgsConstructor
public class EmailService {

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final SecurityConfig securityConfig;


    public String sendForgotPasswordEmail(String email) {
        String newPassword = generateNewPassword();
        sendEmail(email, newPassword); // 이메일 전송

        // 암호화
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        return encodedPassword;
        // return newPassword;
    }

    private void sendEmail(String email, String newPassword) {
        MimeMessage message = javaMailSender.createMimeMessage();   // MimeMessage 를 사용하여 HTML 태그를 사용하지 않는 일반 텍스트 이메일로 설정
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("임시 비밀번호 발급");
            helper.setText("임시 비밀번호는 " + newPassword + "입니다.", false);
            javaMailSender.send(message);
        } catch (Exception e) {
            // 예외 처리
        }
    }

    private String generateNewPassword() {
        // 임시 비밀번호 생성 로직
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

}
