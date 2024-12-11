package com.cuizhanming.template.ai.templatespringai.config;

import com.cuizhanming.template.ai.templatespringai.external.WeatherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class FunctionsConfig {

    @Bean
    @Description("This is a function that returns the weather in given location.")
    public Function<WeatherService.Request, WeatherService.Response> weatherApi() {
        return new WeatherService();
    }

}
