plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	//[SpringBoot]STEP 00 import
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	id("com.github.ben-manes.versions") version "0.51.0"
}

group = "com.ziuc."
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
	 * Spring Core
	 */
	implementation("org.springframework.boot:spring-boot-starter-aop")

	/**
	 * Spring Web
	 */
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	/**
	 * Spring GraphQL
	 */
	//implementation("org.springframework.boot:spring-boot-starter-graphql")

	/**
	 * Spring Security
	 */
	implementation("org.springframework.boot:spring-boot-starter-security")
	testImplementation("org.springframework.security:spring-security-test")
	//Java JWT by `auth0`
	implementation("io.jsonwebtoken:jjwt-api:${property("jjwt.version")}")
	implementation("io.jsonwebtoken:jjwt-impl:${property("jjwt.version")}")
	implementation("io.jsonwebtoken:jjwt-jackson:${property("jjwt.version")}")

	/**
	 * Spring DevTools
	 */
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	runtimeOnly("org.springframework.boot:spring-boot-devtools") {
		isTransitive = false // similar to maven `optional=true`, set transient dependencies should be resolved or not?
	}

	/**
	 * Kotlin JUnit 5
	 */
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
