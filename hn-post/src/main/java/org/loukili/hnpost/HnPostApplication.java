package org.loukili.hnpost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class HnPostApplication {

	public static void main(String[] args) {
		SpringApplication.run(HnPostApplication.class, args);
	}

}
