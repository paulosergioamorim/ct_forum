package br.ufes.ct_forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CtForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(CtForumApplication.class, args);
	}

}
