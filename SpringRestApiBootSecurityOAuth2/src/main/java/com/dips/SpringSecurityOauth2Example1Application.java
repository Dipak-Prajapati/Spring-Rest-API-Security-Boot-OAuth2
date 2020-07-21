package com.dips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@EnableAutoConfiguration
@SpringBootApplication()
public class SpringSecurityOauth2Example1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityOauth2Example1Application.class, args);
	}

}
