package com.anabeatriz.mepaga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MepagaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MepagaApplication.class, args);
	}

}
