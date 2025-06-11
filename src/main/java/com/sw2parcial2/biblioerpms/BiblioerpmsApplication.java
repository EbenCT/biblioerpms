package com.sw2parcial2.biblioerpms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BiblioerpmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiblioerpmsApplication.class, args);
	}

}
