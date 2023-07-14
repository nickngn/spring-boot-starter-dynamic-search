plugins {
	`java-library`
	id("org.springframework.boot") version "3.1.1"
	id("org.sonarqube") version "4.2.1.3168"
}

apply(plugin = "io.spring.dependency-management")

sonar {
	properties {
		property("sonar.projectKey")
		property("sonar.organization")
		property("sonar.host")
	}
}

tasks.bootJar {
	enabled = false
}

group = "com.nickngn"
version = "0.0.1-SNAPSHOT"

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenLocal()
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-json")
	implementation("com.fasterxml.jackson.core:jackson-annotations")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
