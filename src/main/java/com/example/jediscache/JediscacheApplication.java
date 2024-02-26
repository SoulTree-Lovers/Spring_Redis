package com.example.jediscache;

import com.example.jediscache.model.User;
import com.example.jediscache.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class JediscacheApplication implements ApplicationRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(JediscacheApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 어플리케이션 시작 시점에 유저 데이터 저장
		userRepository.save(User.builder().name("kang").email("kang@naver.com").build());
		userRepository.save(User.builder().name("kim").email("kim@naver.com").build());
		userRepository.save(User.builder().name("park").email("park@naver.com").build());
		userRepository.save(User.builder().name("song").email("song@naver.com").build());
	}
}
