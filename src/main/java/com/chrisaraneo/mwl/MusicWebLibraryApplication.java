package com.chrisaraneo.mwl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MusicWebLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicWebLibraryApplication.class, args);
	}
}
