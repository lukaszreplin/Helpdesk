package eu.replin.helpdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class HelpdeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}
}
