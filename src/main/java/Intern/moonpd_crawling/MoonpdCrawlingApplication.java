package Intern.moonpd_crawling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MoonpdCrawlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoonpdCrawlingApplication.class, args);
	}

}
