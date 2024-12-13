package com.cuizhanming.template.ai.templatespringai.controller;

import com.cuizhanming.template.ai.templatespringai.services.OpenAIService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

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

    @PostMapping(value = "/document2vector")
    public String uploadDocumentToVectorStore(@RequestParam("fileToUpload") MultipartFile document) {
        return openAIService.uploadDocumentToVectorStore(document);
    }
    @GetMapping(value = "/document2vector/similarity")
    public String checkSimilarity(@RequestParam(value = "message", defaultValue = "What's the weather in Dublin today?") String message) {
        return openAIService.checkSimilarity(message);
    }
}
