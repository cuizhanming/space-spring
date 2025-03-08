
## Issues

1. `docker-compose` file configuration, should be different in cases `run application class directly` or `gradlew bootRun`
    ```shell
    spring.docker.compose.file=./template-spring-ai-kotlin/docker/compose.yaml
    #v.s.
    spring.docker.compose.file=./docker/compose.yaml
    ```
2. It needs overridding the default http client library from Spring Boot, due to Vector Store connection issue with it.
    ```shell
    implementation("org.apache.httpcomponents.client5:httpclient5")
    ```