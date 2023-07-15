plugins {
	id("org.sonarqube") version "4.2.1.3168"
}

sonar {
	properties {
		property("sonar.projectKey", "nickngn_spring-boot-starter-dynamic-search")
		property("sonar.organization", "afarmerlearntocode")
		property("sonar.host.url", "https://sonarcloud.io")
	}
}

group = "com.nickngn.spring-boot-starter-dynamic-search"
version = "0.1.0"

repositories {
	mavenLocal()
	mavenCentral()
}
