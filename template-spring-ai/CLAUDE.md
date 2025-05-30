# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands
- Run application: `./mvnw spring-boot:run`
- Build project: `./mvnw clean package`
- Run tests: `./mvnw test`
- Run single test: `./mvnw test -Dtest=TestClassName#testMethodName`
- Run with specific profile: `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`

## Code Style Guidelines
- Java version: Java 21
- Use Spring Boot 3.x conventions and dependency injection
- Organize imports: Java core first, then external libraries, then project packages
- Class naming: PascalCase for classes (e.g., OpenAIService)
- Method naming: camelCase for methods (e.g., multimodalAudioAndText)
- Variable naming: camelCase for variables
- Exception handling: Use try-catch with specific exceptions, not generic Exception
- Use Spring annotations (@Service, @Controller, etc.) appropriately
- Prefer constructor injection over field injection
- For multiline strings, use Java text blocks (""" """)
- Use reactive programming (Flux, Mono) for streaming responses when appropriate