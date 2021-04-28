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
		//client.doHttpGet("https://gorest.co.in/public-api/posts");
		//client.doHttpPost("https://gorest.co.in/public-api/users", "{\"name\":\"karen\", \"gender\":\"Female\", \"email\":\"karen.souza@15ce.com\", \"status\":\"Active\"}");
		//client.queryUserbyName("https://gorest.co.in/public-api/users", "karen");
		//client.doHttpPatch("https://gorest.co.in/public-api/users/1396", "{\"name\":\"karen\", \"gender\":\"Female\", \"email\":\"karen.souza@15ce.com\", \"status\":\"Inactive\"}");
		//client.queryUserbyName("https://gorest.co.in/public-api/users/", "karen");
		client.doHttpDelete("https://gorest.co.in/public-api/users/1396");
		client.queryUserbyName("https://gorest.co.in/public-api/users/", "karen");
	}

}
