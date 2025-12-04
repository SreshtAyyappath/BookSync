package com.sresht.booksync;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BooksyncApplication {

	@Value("${spring.datasource.password}")
	private String dbPass;

	public static void main(String[] args) {
		SpringApplication.run(BooksyncApplication.class, args);
	}

}
