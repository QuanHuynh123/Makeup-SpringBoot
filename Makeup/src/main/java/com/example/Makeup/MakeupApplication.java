package com.example.Makeup;

import com.example.Makeup.dto.ProductDTO;
import com.example.Makeup.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MakeupApplication {


	public static void main(String[] args) {
		SpringApplication.run(MakeupApplication.class, args);
	}

}
