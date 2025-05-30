package com.cuizhanming.template.ai.templatespringai.mcp;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


public class McpClientTests {

    @Disabled
    @Test
    public void testMcpServerThroughStdioFromLocal() {
        var stdioParams = ServerParameters.builder("java")
                .args("-jar", "target/template-spring-ai-0.0.1-SNAPSHOT.jar")
                .build();

        var transport = new StdioClientTransport(stdioParams);
        var client = McpClient.sync(transport).build();

        client.initialize();

        McpSchema.ListToolsResult toolsResult = client.listTools();
        System.out.println("Available Tools = " + toolsResult);

        McpSchema.CallToolResult weatherForcastResult = client.callTool(new McpSchema.CallToolRequest("getWeatherForecastByLocation",
                Map.of("latitude", "47.6062", "longitude", "-122.3321")));
        System.out.println("Weather Forcast: " + weatherForcastResult);

        McpSchema.CallToolResult alertResult = client.callTool(new McpSchema.CallToolRequest("getAlerts", Map.of("state", "NY")));
        System.out.println("Alert Response = " + alertResult);

        client.closeGracefully();
    }

    @Disabled
    @Test
    public void testMcpServerThroughSse() {
        var transport = HttpClientSseClientTransport.builder("http://localhost:8080").build();
        var client = McpClient.sync(transport).build();

        client.initialize();

        McpSchema.ListToolsResult toolsResult = client.listTools();
        System.out.println("Available Tools = " + toolsResult);

        McpSchema.CallToolResult weatherForcastResult = client.callTool(new McpSchema.CallToolRequest("getWeatherForecastByLocation",
                Map.of("latitude", "47.6062", "longitude", "-122.3321")));
        System.out.println("Weather Forcast: " + weatherForcastResult);

        McpSchema.CallToolResult alertResult = client.callTool(new McpSchema.CallToolRequest("getAlerts", Map.of("state", "NY")));
        System.out.println("Alert Response = " + alertResult);

        client.closeGracefully();
    }

//    @Disabled
//    @Test
//    public void testMcpServerThroughWebFluxSse() {
//        var transport = new WebFluxSseClientTransport(WebClient.builder().baseUrl("http://localhost:8080"));
//        var client = McpClient.sync(transport).build();
//
//        client.initialize();
//
//        McpSchema.ListToolsResult toolsResult = client.listTools();
//        System.out.println("Available Tools = " + toolsResult);
//
//        McpSchema.CallToolResult weatherForcastResult = client.callTool(new McpSchema.CallToolRequest("getWeatherForecastByLocation",
//                Map.of("latitude", "47.6062", "longitude", "-122.3321")));
//        System.out.println("Weather Forcast: " + weatherForcastResult);
//
//        McpSchema.CallToolResult alertResult = client.callTool(new McpSchema.CallToolRequest("getAlerts", Map.of("state", "NY")));
//        System.out.println("Alert Response = " + alertResult);
//
//        client.closeGracefully();
//    }
}
