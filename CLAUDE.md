# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Overview

This is a Spring Framework template repository containing three main template collections:

- **spring-core**: Spring Framework training materials with Maven-based modules covering Spring essentials, Boot, MVC, Security, and JPA (Java 11, Spring Boot 2.7.x)
- **spring-joshlong**: Josh Long's "Walking tour of Springdom in 2025" demo covering modern Spring features like virtual threads, Spring AI, GraphQL, and observability
- **spring-observation**: Teahouse demo for Spring Boot observability with Prometheus, Grafana, Loki, Tempo, and Spring Boot Admin

## Build Systems & Commands

### Spring Observation (Gradle-based)
```shell
# Start all services
./gradlew bootRun

# Start with MySQL profile
./gradlew bootRun -Pprofiles=mysql

# Run load tests
./gradlew :load-gen:gatlingRun

# Clean all projects and IDE files
./gradlew cleanAll
```

### Spring Core (Maven-based)
```shell
# Build all modules
mvn clean install

# Run tests
mvn test

# Check for dependency updates
mvn versions:display-dependency-updates

# Analyze unused dependencies
mvn dependency:analyze -DignoreNonCompile
```

## Docker & Infrastructure Commands

### Observability Stack
```shell
# Start dependencies (Prometheus, Grafana, etc.)
docker compose up

# Stop and remove data
docker compose down --volumes

# Remove logs
rm -rf logs
```

### Error & Chaos Testing (Spring Observation)
```shell
# Inject errors (missing tea)
make errors

# Fix errors
make errors-fixed

# Inject network latency
make chaos

# Remove latency
make order
```

## Architecture Notes

### Spring Core
- Multi-module Maven project with parent POM managing dependencies
- Organized by training sections: Spring Essentials (Days 1-2) and Spring Boot (Days 3-4)
- Each module has base and solution versions
- Uses HSQLDB for database operations
- Includes common rewards application domain across modules

### Spring Observation
- Gradle multi-project build with dependency locking
- Microservices architecture: Tea Service (8090), Tealeaf Service (8091), Water Service (8092)
- Full observability stack: Prometheus (9090), Grafana (3000), Loki, Tempo
- Service discovery with Eureka (8761) and Spring Boot Admin (8080)
- Uses Java 24, Spring Boot 3.x, Spring Cloud 2025.0.0
- ToxiProxy for network failure injection
- Gatling for load testing

### Spring Josh Long
- Maven-based demo modules showcasing modern Spring features
- Covers: Spring AI, virtual threads, GraphQL, gRPC, Spring Shell, WebAuthn, OAuth
- Includes Spring Batch, Spring Integration, Spring AMQP examples
- Demonstrates observability with Micrometer and SBOMs

## Key URLs (Spring Observation)
- Tea UI: http://localhost:8090/steep  
- Spring Boot Admin: http://localhost:8080
- Eureka: http://localhost:8761
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000
- ToxiProxy UI: http://localhost:8484
- Adminer (DB): http://localhost:8888 (root:password)

## Testing
- Spring Core: Uses `**/*Tests.java` pattern for Maven Surefire
- Spring Observation: JUnit 5 with Rest Assured for integration tests
- Load testing available via Gatling in observability template