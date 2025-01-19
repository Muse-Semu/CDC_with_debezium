package com.debzium.debzium_master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class DebziumMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DebziumMasterApplication.class, args);
	}

}
