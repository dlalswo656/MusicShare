package MusicShare.MusicShare.user.service;

import MusicShare.MusicShare.user.config.SecurityConfig;
import MusicShare.MusicShare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
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
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("임시 비밀번호 발급");
        message.setText("임시 비밀번호는 " + newPassword + "입니다.");
        javaMailSender.send(message);
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
