rootProject.name = "template-kotlin-spring-reactive"
pluginManagement {
    val kotlinVersion: String by extra

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
    }
}