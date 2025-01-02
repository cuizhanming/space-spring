package com.cuizhanming.template.ai.templatespringai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class OpenAIConfig {


    @Bean
    public ChatClient chatClient(@Value("${template.openai.prompt.system:classpath:/openai/prompt/system.txt}") Resource promptSystem,
                                 ChatClient.Builder chatClientBuilder,
                                 VectorStore vectorStore) {
        return chatClientBuilder
                .defaultSystem(promptSystem)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory()), // Chat memory advisor
                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().build()) // RAG advisor
                )
                .build();
    }
}
