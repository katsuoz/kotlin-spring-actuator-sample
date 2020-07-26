import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    java
    war
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    id("com.palantir.docker") version "0.25.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

springBoot {
	mainClassName = "com.example.demo.MainApplicationKt"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
}

tasks {
    withType<Wrapper> {
        gradleVersion = "6.5.1"
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    docker {
        dependsOn(bootWar.get())

        val warArchiveFile = bootWar.get().archiveFile.get().getAsFile()

        name = "mizunashi-mana/spring-actuator-demo"
        setDockerfile(file("Dockerfile"))
        files(warArchiveFile)
        buildArgs(
            mapOf(
                "WAR_FILE" to warArchiveFile.name
            )
        )
    }
}
