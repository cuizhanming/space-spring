
## Build runnable Spring Boot JAR
```bash
mvn clean compile

mvn clean package
OR
mvn clean install
```

## Build Docker compose 
```bash
docker-compose build
```

## Running Docker compose
```bash
 docker compose -f docker-compose.yml up -d

 docker compose -f docker-compose.yml down
```

## Test with Newman
```bash
newman run src/test/resources/newman/test.postman_collection.json -e src/test/resources/newman/Local.postman_environment.json
```

## MySQL

- [Spring Data MySQL](https://github.com/spring-guides/gs-accessing-data-mysql/tree/main)

## Cassandra

- []()

## MongoDB

- []()

## CloudSQL

- []()