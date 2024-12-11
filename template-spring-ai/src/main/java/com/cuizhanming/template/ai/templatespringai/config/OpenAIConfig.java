package com.cuizhanming.template.ai.templatespringai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class OpenAIConfig {


    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder,
                                 @Value("${template.openai.prompt.system:classpath:/openai/prompt/system.txt}") Resource promptSystem) {
        return chatClientBuilder
                .defaultSystem(promptSystem)
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }
}
