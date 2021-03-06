package xyz.wolkarkadiusz.githubservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
public class GithubServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubServiceApplication.class, args);
	}
}
