import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.7.1"
}

group = "no.knowit"
version = "1.0-SNAPSHOT"

repositories {
    maven { url = uri("https://packages.confluent.io/maven/") }
    mavenCentral()
}

dependencies {
    implementation("org.apache.kafka:kafka-clients:3.4.0")
    implementation("org.apache.kafka:kafka-streams:3.4.0")
    implementation("io.confluent:kafka-avro-serializer:7.4.0")
    implementation("io.confluent:kafka-streams-avro-serde:7.4.0")
    implementation("ch.qos.logback:logback-core:1.4.7")
    implementation("ch.qos.logback:logback-classic:1.4.7")
    implementation("org.slf4j:slf4j-api:2.0.7")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

avro {
    isCreateSetters.set(true)
    isCreateOptionalGetters.set(false)
    isGettersReturnOptional.set(false)
    isOptionalGettersForNullableFieldsOnly.set(false)
    fieldVisibility.set("PRIVATE")
    outputCharacterEncoding.set("UTF-8")
    templateDirectory.set(null as String?)
    isEnableDecimalLogicalType.set(true)
}