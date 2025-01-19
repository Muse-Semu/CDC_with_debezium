package com.debzium_solidier.customer_solider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class CustomerSoliderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerSoliderApplication.class, args);
	}

}
