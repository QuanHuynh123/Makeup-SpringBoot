package com.example.Makeup;

import com.example.Makeup.dto.ProductDTO;
import com.example.Makeup.service.ProductService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.*;
import java.util.List;

@SpringBootApplication
public class MakeupApplication {


	public static void main(String[] args) throws IOException {
		SpringApplication.run(MakeupApplication.class, args);

//        File file = new File("src/main/resources/notification-3ec58-firebase-adminsdk-enfqk-016b2bdd4d.json");
//        FileInputStream serviceAccount = new FileInputStream(file.getAbsoluteFile());
//
//		FirebaseOptions options = new FirebaseOptions.Builder()
//				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
//				.build();
//		FirebaseApp.initializeApp(options);
	}

}
