package MusicShare.MusicShare.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class}) // Security 보안 자동 설정 비활성화
public class MusicShareApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicShareApplication.class, args);
	}
}
