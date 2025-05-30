package com.cuizhanming.template.ai.templatespringai;

import com.cuizhanming.template.ai.templatespringai.services.RestfulWeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TemplateSpringAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateSpringAiApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider weatherTools(RestfulWeatherService restfulWeatherService) {
        return MethodToolCallbackProvider.builder().toolObjects(restfulWeatherService).build();
    }

//    @Bean
//    @Description("This is a function that returns the weather in given location.")
//    public Function<WeatherService.Request, WeatherService.Response> weatherApi() {
//        return new WeatherService();
//    }

//    @Bean
//    public CommandLineRunner chatBot(OpenAIService openAIService) {
//        return args -> {
//            try (Scanner scanner = new Scanner(System.in)) {
//
//                // Audio utility to record and playback the audio.
//                Audio audio = new Audio();
//
//                // Start the chat loop
//                while (true) {
//                    //Record user's voice input
//                    System.out.print("\nStart recording your question by press <R>, and then <Enter> to finish recording! ");
//                    if (scanner.hasNext() && scanner.nextLine().equalsIgnoreCase("R")) {
//                        audio.startRecording();
//                        scanner.nextLine();
//                        audio.stopRecording();
//                    }
//
//                    System.out.print("PROCESSING ...\n");
//                    // Send user's input to the AI model and get the response
//                    AssistantMessage response = openAIService.multimodalAudioAndText(audio.getLastRecording());
//                    // Print the text (e.g. transcription) response
//                    System.out.println("\nASSISTANT: " + response.getText());
//                    // Play the audio response, NOT release in M4 yet
//                    // Audio.play(response.getMedia().get(0).getDataAsByteArray());
//                }
//            }
//
//        };
//    }
}
