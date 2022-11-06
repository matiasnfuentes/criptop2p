import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.3"
	id("io.spring.dependency-management") version "1.0.13.RELEASE"
	war
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
	id("jacoco")
	id("org.sonarqube") version "3.3"
}

group = "ar.edu.unq.criptop2p"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

sonarqube {
	properties {
		property("sonar.projectKey", "matiasnfuentes_criptop2p")
		property("sonar.organization", "criptop2p")
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.sources", "src/main/kotlin/ar/edu/unq/criptop2p/model")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.11")
	implementation("org.springframework.boot:spring-boot-starter-validation:2.7.3")
	implementation("org.springframework.security:spring-security-crypto:5.7.3")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	testImplementation(kotlin("test"))
	testImplementation("com.ninja-squad:springmockk:3.1.1")
	testImplementation("com.tngtech.archunit:archunit-junit5-api:1.0.0")
	testImplementation("com.tngtech.archunit:archunit-junit5-engine:1.0.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
	}
}
