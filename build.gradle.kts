import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.2.10"
    id("com.gradleup.shadow") version "9.1.0"
    application
}

group = "app.wezik.aoc"

repositories {
    mavenCentral()
}

application {
    // fallback
    mainClass = "app.wezik.aoc.cli.aoc.AocCommandKt"
}


// NOTE: splits regular CLI and test runner CLI into 2 separate jars, one for each entrypoint
tasks {
    val aocJar by registering(ShadowJar::class) {
        archiveBaseName.set("aoc")
        archiveClassifier.set("")
        archiveVersion.set("")

        manifest {
            attributes["Main-Class"] = "app.wezik.aoc.cli.aoc.AocCommandKt"
        }

        from(sourceSets.main.get().output)
        configurations = listOf(project.configurations.runtimeClasspath.get())
    }

    val aotJar by registering(ShadowJar::class) {
        archiveBaseName.set("aot")
        archiveClassifier.set("")
        archiveVersion.set("")

        manifest {
            attributes["Main-Class"] = "app.wezik.aoc.cli.aot.AotCommandKt"
        }

        from(sourceSets.main.get().output)
        configurations = listOf(project.configurations.runtimeClasspath.get())
    }

    register("buildFat") {
        dependsOn(aocJar, aotJar)
    }

    val runAoc by registering(JavaExec::class) {
        group = "application"
        description = "Run the AOC CLI"
        mainClass.set("app.wezik.aoc.cli.aoc.AocCommandKt")
        classpath = sourceSets.main.get().runtimeClasspath
    }

    val runAot by registering(JavaExec::class) {
        group = "application"
        description = "Run the AOT CLI"
        mainClass.set("app.wezik.aoc.cli.aot.AotCommandKt")
        classpath = sourceSets.main.get().runtimeClasspath
    }
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
