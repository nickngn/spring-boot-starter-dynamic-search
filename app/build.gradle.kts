/*
 * MIT License
 *
 * Copyright (c) [2023] [NickNgn]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

plugins {
	`java-library`
	id("org.sonarqube") version "4.2.1.3168"
	id("org.springframework.boot") version "3.1.1"
	`maven-publish`
	signing
}

sonar {
	properties {
		property("sonar.projectKey", "nickngn_spring-boot-starter-dynamic-search")
		property("sonar.organization", "afarmerlearntocode")
		property("sonar.host.url", "https://sonarcloud.io")
	}
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
			artifactId = "spring-boot-starter-dynamic-search"
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
						url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
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