package com.ln.taskMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TaskMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskMicroserviceApplication.class, args);
	}

}
