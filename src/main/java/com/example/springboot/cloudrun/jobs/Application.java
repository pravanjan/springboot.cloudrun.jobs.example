package com.example.springboot.cloudrun.jobs;

import com.example.springboot.cloudrun.jobs.migration.Migration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		Migration.run();
		System.out.println("The migration is completed");
		SpringApplication.exit(context); // As it's one time finished we exit the application
	}

}
