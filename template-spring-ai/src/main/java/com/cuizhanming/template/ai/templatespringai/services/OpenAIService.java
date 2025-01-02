package com.cuizhanming.template.ai.templatespringai.services;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.*;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OpenAIService {

    private static final String DEFAULT_PROMPT = """
            You are a very funny comedian!
            """;

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final OpenAiImageModel openAiImageModel;
    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;
    private final OpenAiAudioSpeechModel openAiAudioSpeechModel;

    public OpenAIService(ChatClient chatClient, VectorStore vectorStore, OpenAiImageModel openAiImageModel, OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel, OpenAiAudioSpeechModel openAiAudioSpeechModel) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
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
                .language("en")
                .prompt("Transcribe the following audio")
//                .granularityType(OpenAiAudioApi.TranscriptionRequest.GranularityType.WORD)
                .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .temperature(0f)
                .model(OpenAiAudioApi.WhisperModel.WHISPER_1.getValue())
                .build();

        var audioFile = new ClassPathResource("audio/countdown.mp3");
        var audioTranscriptionPrompt = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
        var response = openAiAudioTranscriptionModel.call(audioTranscriptionPrompt);

        return response.getResult().getOutput();
    }

    public String text2audio(String userMessage) {
        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
                .model(OpenAiAudioApi.TtsModel.TTS_1.value)
                .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .speed(1.0f)
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
                .function("weatherApi")
                .model("gpt-4o-mini")
                .build();
        Prompt prompt = new Prompt(List.of(userMessage), options);

        ChatResponse response = chatClient.prompt(prompt)
                .call()
                .chatResponse();

        return response.getResult().getOutput().getContent();
    }

    /**
     * MultiModality API
     * <a href="https://docs.spring.io/spring-ai/reference/api/multimodality.html">Spring AI MultiModality API</a>
     **/
    public AssistantMessage multimodalAudioAndText(byte[] userAudioInput) {
        UserMessage userMessage = new UserMessage("Please answer the questions in the audio input",
                new Media(MediaType.parseMediaType("audio/wav"), new ByteArrayResource(userAudioInput)));
        return chatClient.prompt()
                .messages(userMessage)
                .call()
                .chatResponse()
                .getResult()
                .getOutput();
    }

    public String uploadDocumentToVectorStore(MultipartFile document) {
        var tikaDocumentReader = new TikaDocumentReader(document.getResource());
        // uses CL100K_BASE encoding from jtokkit library
        //var splitter = new TokenTextSplitter();
        var splitter = new TokenTextSplitter(1000, 400, 10, 5000, true);
        vectorStore.write(splitter.split(tikaDocumentReader.read()));
        return "ok";
    }

    public String checkSimilarity(String message) {
        return vectorStore.similaritySearch(message).stream().map(Document::getMetadata)
                // convert metadata to key:value pairs
                .flatMap(m -> m.entrySet().stream().map(e -> e.getKey() + " : " + e.getValue()))
                .collect(Collectors.joining("\n"));
    }
}
