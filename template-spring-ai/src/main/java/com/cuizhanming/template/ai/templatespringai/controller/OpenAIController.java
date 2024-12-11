package com.cuizhanming.template.ai.templatespringai.controller;

import com.cuizhanming.template.ai.templatespringai.services.OpenAIService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/openai")
public class OpenAIController {

    private OpenAIService openAIService;

    public OpenAIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/chat")
    public String generation(@RequestParam(value = "message", defaultValue = "Give me a joke") String message) {
        return openAIService.chat(message);
    }

    @GetMapping("/streaming")
    public Flux<String> streaming(@RequestParam(value = "message", defaultValue = "Give me a joke") String message) {
        return openAIService.streaming(message);
    }

    @GetMapping(value = "/image", produces = "text/html")
    public String image(@RequestParam(value = "message", defaultValue = "A cute cat") String message) {
        return "<img src=\"" + openAIService.imageUrl(message) + "\"/>";
    }

    @GetMapping(value = "/audio2text")
    public String audio2text() {
        return openAIService.audio2text();
    }

    @GetMapping(value = "/text2audio")
    public String text2audio(@RequestParam(value = "message", defaultValue = "Hello, Spring AI") String message) {
        return openAIService.text2audio(message);
    }

    @GetMapping(value = "/function")
    public String function(@RequestParam(value = "message", defaultValue = "What's the weather in Dublin today?") String message) {
        return openAIService.callFunction(message);
    }
}
