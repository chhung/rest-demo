package com.workflow.restdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.workflow.restdemo.application.ClientImpl;

@SpringBootApplication
public class RestDemoApplication implements ApplicationRunner {

	@Autowired
	private ClientImpl client;
	
	public static void main(String[] args) {
		SpringApplication.run(RestDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("hello");
		client.doHttpGet("https://tw.yahoo.com/");
	}

}
