package com.example.interviewapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class InterviewappApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewappApplication.class, args);
	}

}
