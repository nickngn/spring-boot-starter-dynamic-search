plugins {
	`java-library`
	id("org.springframework.boot") version "3.1.1"
	`maven-publish`
	signing
}

apply(plugin = "io.spring.dependency-management")

group = "io.github.nickngn"
version = "0.1.0"

tasks.bootJar {
	enabled = false
}

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

java {
	withJavadocJar()
	withSourcesJar()
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			artifactId = "spring-boot-starter-dynamic-search-jakarta"
			from(components["java"])
			versionMapping {
				usage("java-api") {
					fromResolutionOf("runtimeClasspath")
				}
				usage("java-runtime") {
					fromResolutionResult()
				}
			}
			pom {
				name.set("Spring Boot Starter Dynamic Search")
				description.set("Additional module for dynamic search with Spring Data JPA")
				url.set("https://github.com/nickngn/spring-boot-starter-dynamic-search")
				properties.set(mapOf(
						"myProp" to "value",
						"prop.with.dots" to "anotherValue"
				))
				licenses {
					license {
						name.set("The Apache License, Version 2.0")
						url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
					}
				}
				developers {
					developer {
						id.set("NickNgn")
						name.set("Hung Nguyen")
						email.set("hungnd.iam@gmail.com")
					}
				}
				scm {
					connection.set("scm:git:https://github.com/nickngn/spring-boot-starter-dynamic-search.git")
					developerConnection.set("scm:git:ssh://github.com/nickngn/spring-boot-starter-dynamic-search.git")
					url.set("https://github.com/nickngn/spring-boot-starter-dynamic-search")
				}
			}

		}
		repositories {
			maven {
				val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
				val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
				url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)

				credentials {
					username = project.property("ossrhUsername").toString()
					password = project.property("ossrhPassword").toString()
				}
			}
		}
	}
}

signing {
	useGpgCmd()
	sign(publishing.publications["maven"])
}

tasks.javadoc {
	if (JavaVersion.current().isJava9Compatible) {
		(options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
	}
}