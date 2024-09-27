rootProject.name = "template-kotlin-springboot-2024"
pluginManagement {
    val kotlinVersion: String by extra

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
    }
}