plugins {
	java
	id("org.springframework.boot") version "3.0.4"
}

apply(plugin = "io.spring.dependency-management")

group = "com.cuizhanming"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("org.projectlombok:lombok")
	implementation("org.mapstruct:mapstruct:1.5.3.Final")


	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
