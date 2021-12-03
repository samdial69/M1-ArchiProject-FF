package fr.univlorrainem1archi.friendsfiestas_v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FriendsFiestasV1Application {

	public static void main(String[] args) {
		SpringApplication.run(FriendsFiestasV1Application.class, args);
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){return new BCryptPasswordEncoder();}
}
