package com.cuizhanming.template.ai.templatespringai.external;

import java.util.function.Function;

public class WeatherService implements Function<WeatherService.Request, WeatherService.Response> {

    @Override
    public Response apply(Request request) {
        if (request.location().equalsIgnoreCase("Dublin")) {
            return new Response("Dublin", "Rainy");
        } else {
            return new Response(request.location, "Unknown");
        }
    }

    public record Request(String location) { }
    public record Response(String location, String weather) { }
}
