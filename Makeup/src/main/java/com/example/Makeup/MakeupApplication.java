package com.example.Makeup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.*;
@Slf4j
@SpringBootApplication
@EnableCaching
@EnableRabbit
public class MakeupApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MakeupApplication.class, args);

		log.info("Makeup Application Started Successfully");
	}

}
