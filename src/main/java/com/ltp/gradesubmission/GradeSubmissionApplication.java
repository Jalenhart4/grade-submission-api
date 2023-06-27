package com.ltp.gradesubmission;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication @AllArgsConstructor
public class GradeSubmissionApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GradeSubmissionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}	


}
