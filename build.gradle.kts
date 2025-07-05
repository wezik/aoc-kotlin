plugins {
    kotlin("jvm") version "2.2.0"
    id("com.gradleup.shadow") version "8.3.8"
    application
}

group = "app.wezik.aoc"

repositories {
    mavenCentral()
}

application {
    mainClass = "app.wezik.aoc.MainKt"
}

dependencies {
    // ArrowKT
    implementation(platform("io.arrow-kt:arrow-stack:2.1.2"))
    implementation("io.arrow-kt:arrow-core")

    // reflections
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // cli
    implementation("com.github.ajalt.clikt:clikt:5.0.3")
}

kotlin {
    jvmToolchain(21)
}
