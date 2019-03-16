package com.sendfriend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.sql.DataSource;

@SpringBootApplication
@EntityScan("com.sendfriend.domain")
public class SendFriendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendFriendApplication.class, args);
	}
}
