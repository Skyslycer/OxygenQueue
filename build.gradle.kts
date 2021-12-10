import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

val compileKotlin: KotlinCompile by tasks
val shadePattern = "de.skyslycer.oxygenqueue.shade"

group = "de.skyslycer"
version = "1.0.1"

repositories {
    mavenCentral()
    maven("https://nexus.velocitypowered.com/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.0.1")

    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")

    implementation(kotlin("stdlib"))
    implementation("com.akuleshov7:ktoml-core:0.2.8")
    implementation("com.akuleshov7:ktoml-file:0.2.8")
}

compileKotlin.kotlinOptions.jvmTarget = "17"

tasks.build {
    dependsOn("shadowJar")
}

tasks.shadowJar {
    relocate("$shadePattern.minimessage", "net.kyori.adventure.text.minimessage")
    relocate("$shadePattern.toml", "com.akuleshov7.ktoml")
}