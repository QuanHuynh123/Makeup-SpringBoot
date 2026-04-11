package com.example.Makeup.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class OpenAiChatConfig {

  @Bean
  public ChatClient makeupChatClient(
      ChatClient.Builder chatClientBuilder,
      @Value("classpath:AI-knowledge.txt") Resource knowledgeResource)
      throws IOException {

    String knowledge =
        new String(knowledgeResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

    String systemPrompt =
        "Bạn là trợ lý tư vấn cho hệ thống Makeup/Cosplay. "
            + "Ưu tiên trả lời ngắn gọn, rõ ràng, chính xác nghiệp vụ. "
            + "Không bịa thông tin ngoài ngữ cảnh đã biết. "
            + "Khi thiếu dữ liệu, hãy nói rõ và gợi ý người dùng liên hệ staff. "
            + "Ngữ cảnh nghiệp vụ:\n"
            + knowledge;

    return chatClientBuilder.defaultSystem(systemPrompt).build();
  }
}