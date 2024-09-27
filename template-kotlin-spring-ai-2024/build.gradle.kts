plugins {
    kotlin("jvm") version "1.9.25"
}

group = "com.cuizhanming.ai"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.deeplearning4j:deeplearning4j-core:1.0.0-beta7")
    implementation("org.nd4j:nd4j-native-platform:1.0.0-beta7")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}