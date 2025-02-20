plugins {
    kotlin("jvm") version "2.1.10"
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
    implementation(platform("io.arrow-kt:arrow-stack:1.2.4"))
    implementation("io.arrow-kt:arrow-core")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
}

kotlin {
    jvmToolchain(21)
}
