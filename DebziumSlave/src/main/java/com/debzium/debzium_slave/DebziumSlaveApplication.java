package com.debzium.debzium_slave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class DebziumSlaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(DebziumSlaveApplication.class, args);
	}

}
