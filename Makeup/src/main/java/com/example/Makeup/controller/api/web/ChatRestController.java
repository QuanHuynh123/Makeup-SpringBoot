//package com.example.Makeup.controller.api.web;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/chat")
//public class ChatRestController {
//
//    private final ChatClient chatClient;
//
//    @PostMapping("/send")
//    public String sendMessage(@RequestParam  String message) {
//
//        return chatClient.prompt()
//                .user(message)
//                .call()
//                .content();
//    }
//
//    @GetMapping("/stream")
//    public Flux<String> streamMessage(@RequestParam String message) {
//        return chatClient.prompt()
//                .user(message)
//                .stream()
//                .content();
//    }
//}
