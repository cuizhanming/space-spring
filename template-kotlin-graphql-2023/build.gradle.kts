import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.2"
	id("org.graalvm.buildtools.native") version "0.9.23"
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("com.netflix.dgs.codegen") version "6.0.2"
}

group = "com.cuizhanming"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-graphql")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	//DGS BOM/platform dependency. This is the only place you set version of DGS
//	implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:3.10.2"))
//	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
//	implementation("com.netflix.graphql.dgs:graphql-dgs-subscriptions-websockets-autoconfigure")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.graphql:spring-graphql-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.generateJava {
	language = "kotlin"
	schemaPaths = mutableListOf("${projectDir}/src/main/resources/graphql") // The GraphQL schema file
	packageName = "com.cuizhanming.template.kotlin.codegen" // The package name to use to generate sources
//	generateClientv2 = true // Enable generating the type safe query API
}

