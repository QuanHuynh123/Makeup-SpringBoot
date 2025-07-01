package com.example.Makeup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.*;
@Slf4j
@SpringBootApplication
@EnableCaching
public class MakeupApplication {


	public static void main(String[] args) throws IOException {
		SpringApplication.run(MakeupApplication.class, args);

		log.info("Makeup Application Started Successfully");

//        File file = new File("src/main/resources/notification-3ec58-firebase-adminsdk-enfqk-016b2bdd4d.json");
//        FileInputStream serviceAccount = new FileInputStream(file.getAbsoluteFile());
//
//		FirebaseOptions options = new FirebaseOptions.Builder()
//				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
//				.build();
//		FirebaseApp.initializeApp(options);
	}

}
