package com.cuizhanming.template.ai.templatespringai.services;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.*;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class OpenAIService {

    private static final String DEFAULT_PROMPT = """
            You are a very funny comedian!
            """;

    private final ChatClient chatClient;
    private final OpenAiImageModel openAiImageModel;
    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;
    private final OpenAiAudioSpeechModel openAiAudioSpeechModel;

    public OpenAIService(ChatClient chatClient, OpenAiImageModel openAiImageModel, OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel, OpenAiAudioSpeechModel openAiAudioSpeechModel) {
        this.chatClient = chatClient;
        this.openAiImageModel = openAiImageModel;
        this.openAiAudioTranscriptionModel = openAiAudioTranscriptionModel;
        this.openAiAudioSpeechModel = openAiAudioSpeechModel;
    }

    public String chat(String userMessage) {
        return chatClient.prompt(DEFAULT_PROMPT)
                .user(userMessage)
                .call()
                .content();
    }

    public Flux<String> streaming(String userMessage) {
        return chatClient.prompt(DEFAULT_PROMPT)
                .user(userMessage)
                .stream()
                .content();
    }

    public String imageUrl(String userMessage) {
        var openAiImageOptions = OpenAiImageOptions.builder()
                .withModel(OpenAiImageApi.ImageModel.DALL_E_3.getValue())
                .withQuality("hd")
                .withN(1)
                .withHeight(1024)
                .withWidth(1024)
                .build();
        var imagePrompt = new ImagePrompt(userMessage, openAiImageOptions);
        ImageResponse imageResponse = openAiImageModel.call(imagePrompt);
        return imageResponse.getResult().getOutput().getUrl();
    }

    public String audio2text() {
        var transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                // translate the audio to specified language
                .withLanguage("en")
                .withPrompt("Transcribe the following audio")
                //.withGranularityType(OpenAiAudioApi.TranscriptionRequest.GranularityType.WORD)
                .withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .withTemperature(0f)
                .withModel(OpenAiAudioApi.WhisperModel.WHISPER_1.getValue())
                .build();

        var audioFile = new ClassPathResource("audio/countdown.mp3");
        var audioTranscriptionPrompt = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
        var response = openAiAudioTranscriptionModel.call(audioTranscriptionPrompt);

        return response.getResult().getOutput();
    }

    public String text2audio(String userMessage) {
        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
                .withModel(OpenAiAudioApi.TtsModel.TTS_1.value)
                .withVoice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .withSpeed(1.0f)
                .build();

        SpeechPrompt prompt = new SpeechPrompt(userMessage, speechOptions);

        SpeechResponse response = openAiAudioSpeechModel.call(prompt);

        byte[] body = response.getResult().getOutput();

        // a method to save byte[] to mp3 file at resources/audio
        saveAudio(body, UUID.randomUUID().toString());

        return "OK";
    }

    private void saveAudio(byte[] body, String fileName) {
        try {
            var fileOutputStream = new FileOutputStream(fileName+".mp3");
            fileOutputStream.write(body);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String callFunction(String message) {

        UserMessage userMessage = new UserMessage(message);
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withFunction("weatherApi")
                .withModel("gpt-4o-mini")
                .build();
        Prompt prompt = new Prompt(List.of(userMessage), options);

        ChatResponse response = chatClient.prompt(prompt)
                .call()
                .chatResponse();

        return response.getResult().getOutput().getContent();
    }
}
