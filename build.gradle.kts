plugins {
    kotlin("jvm") version "2.1.0"

    id("com.adarshr.test-logger") version "4.0.0"
    application
}

group = "app.wezik.aoc2024"

repositories {
    mavenCentral()
}

application {
    mainClass = "app.wezik.aoc2024.MainKt"
}

val kotestVersion = "5.9.0"

dependencies {
    /// ArrowKT
    implementation(platform("io.arrow-kt:arrow-stack:1.2.4"))
    implementation("io.arrow-kt:arrow-core")
    implementation("io.arrow-kt:arrow-fx-coroutines")

    // KoTest
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
