package com.cuizhanming.template.ai.templatespringai;

import com.cuizhanming.template.ai.templatespringai.services.OpenAIService;
import com.cuizhanming.template.ai.templatespringai.utils.Audio;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;

import java.util.Scanner;

@SpringBootApplication
public class TemplateSpringAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateSpringAiApplication.class, args);
    }

    @Bean
    public CommandLineRunner chatBot(OpenAIService openAIService) {
        return args -> {
            try (Scanner scanner = new Scanner(System.in)) {

                // Audio utility to record and playback the audio.
                Audio audio = new Audio();

                // Start the chat loop
                while (true) {
                    //Record user's voice input
                    audio.startRecording();
                    System.out.print("\nRecording your question ... press <Enter> to stop! ");
                    scanner.nextLine();
                    audio.stopRecording();

                    System.out.print("PROCESSING ...\n");

                    // Send user's input to the AI model and get the response
                    AssistantMessage response = openAIService.multimodalAudioAndText(audio.getLastRecording());

                    // Print the text (e.g. transcription) response
                    System.out.println("\nASSISTANT: " + response.getContent());
                    // Play the audio response
                    Audio.play(response.getMedia().get(0).getDataAsByteArray());
                }
            }

        };
    }
}
