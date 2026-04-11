package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.request.ChatRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRestController {

	private final ChatClient makeupChatClient;

	@PostMapping("/send")
	public ApiResponse<String> sendMessage(@RequestBody ChatRequest request) {
		if (request == null || request.getMessage() == null || request.getMessage().isBlank()) {
			return ApiResponse.error(400, "Message is required");
		}

		try {
			String content = makeupChatClient.prompt().user(request.getMessage()).call().content();
			return ApiResponse.success("Chat success", content);
		} catch (Exception ex) {
			return ApiResponse.error(503, "AI service is temporarily unavailable");
		}
	}

	@GetMapping("/stream")
	public Flux<String> streamMessage(@RequestParam String message) {
		if (message == null || message.isBlank()) {
			return Flux.just("Message is required");
		}

		return makeupChatClient.prompt().user(message).stream().content();
	}
}
