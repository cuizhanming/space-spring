plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	kotlin("plugin.jpa") version "1.9.25"
	id("org.springframework.boot") version "3.3.7"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.github.ben-manes.versions") version "0.51.0"
}

group = "com.cuizhaming.template"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	/**
	 * Kotlin support
	 */
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	/**
	 * SpringBoot
	 */
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-aop")

	/**
	 * Spring GraphQL
	 */
	//implementation("org.springframework.boot:spring-boot-starter-graphql")

	/**
	 * SpringBoot Security
	 */
	implementation("org.springframework.boot:spring-boot-starter-security")
	// Java JWT by `auth0`
	implementation("io.jsonwebtoken:jjwt-api:${property("jjwt.version")}")
	implementation("io.jsonwebtoken:jjwt-impl:${property("jjwt.version")}")
	implementation("io.jsonwebtoken:jjwt-jackson:${property("jjwt.version")}")

	/**
	 * SpringBoot DevTools
	 */
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	runtimeOnly("org.springframework.boot:spring-boot-devtools") {
		isTransitive = false // similar to maven `optional=true`, set transient dependencies should be resolved or not?
	}

	/**
	 * SpringBoot Docker Compose
	 */
	runtimeOnly("org.springframework.boot:spring-boot-docker-compose")

	/**
	 * Tests with JUnit5
	 */
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	/**
	 * Test with TestContainers following this article:
	 * https://www.sivalabs.in/run-spring-boot-testcontainers-tests-at-jet-speed/
	 */
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")

}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
