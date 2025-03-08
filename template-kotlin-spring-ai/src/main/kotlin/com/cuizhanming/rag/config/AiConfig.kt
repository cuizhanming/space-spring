package com.cuizhanming.rag.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AiConfig {

    @Bean
    fun chatClient(chatClientBuilder: ChatClient.Builder) =
        chatClientBuilder.defaultAdvisors { MessageChatMemoryAdvisor.builder(InMemoryChatMemory()).build() }.build()

} 