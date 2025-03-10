plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("kapt") version "1.9.25"
}

group = "com"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Spring-Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // MySQL
    runtimeOnly("com.mysql:mysql-connector-j")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    implementation("mysql:mysql-connector-java:8.0.33")
    testRuntimeOnly("com.h2database:h2")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    // Spring Boot DevTools
    implementation("org.springframework.boot:spring-boot-starter")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("me.paulschwarz:spring-dotenv:4.0.0")

    // OAuth2
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.json:json:20231013")

    // QueryDSL Kotlin
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
//    annotationProcessor("com.querydsl:querydsl-apt:5.1.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    // Spring Retry
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    // WebSocket
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    // Thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    //Kotlin 관련 설정
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Mongo
    implementation ("org.springframework.boot:spring-boot-starter-data-mongodb")

    // Kotlin Logging
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.1")

    // 코루틴
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}


/*
// QueryDSL 빌드 옵션 (옵션)
val generated = "src/main/generated"

// QueryDSL QClass 파일 생성 위치 설정
tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(file(generated))
}

// Java source set에 QueryDSL QClass 위치 추가
sourceSets.main {
    java.srcDirs(generated)
}

// Gradle clean 시 QClass 디렉토리 삭제
tasks.clean {
    delete(generated)
}*/
tasks.jar {
    enabled = false
}
kapt {
    keepJavacAnnotationProcessors = true
}
