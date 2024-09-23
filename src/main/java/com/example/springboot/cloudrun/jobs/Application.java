package com.example.springboot.cloudrun.jobs;

import com.example.springboot.cloudrun.jobs.migration.Migration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		SpringApplication.run(Application.class, args);
		Migration.run();
	}

}
