import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.40"
    jacoco

    id("org.jetbrains.dokka") version "0.9.16"
    `maven-publish`
    signing

}

group = "io.github.rybalkinsd"
version = "0.10.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.2.1")

    val jacksonVersion = "2.9.9"
    implementation(jackson("core"), "jackson-databind", jacksonVersion)
    implementation(jackson("dataformat"), "jackson-dataformat-yaml", jacksonVersion)
    implementation(jackson("module"), "jackson-module-kotlin", jacksonVersion)
    api("com.squareup.okhttp3", "okhttp", "3.14.2")

    testImplementation(kotlin("test-junit"))
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.mock-server:mockserver-netty:5.5.4")
    testImplementation("org.mock-server:mockserver-client-java:5.5.4")
    testImplementation("org.assertj:assertj-core:3.12.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<JacocoReport> {
    reports {
        xml.isEnabled = true
        xml.destination = File("$buildDir/reports/jacoco/report.xml")
        html.isEnabled = false
    }
}

val sourcesJar = task<Jar>("sourcesJar") {
    from(sourceSets.main.get().allJava)
    classifier = "sources"
}

val dokkaJar = task<Jar>("dokkaJar") {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    from(tasks.dokka)
    classifier = "javadoc"
}

publishing {
    publications {
        create<MavenPublication>("kohttp") {
            from(components["java"])
            artifacts {
                artifact(sourcesJar)
                artifact(dokkaJar)
            }

            pom {
                name.set("Kotlin http dsl")
                description.set("Kotlin http dsl based on okhttp")
                url.set("https://github.com/rybalkinsd/kohttp")
                organization {
                    name.set("io.github.rybalkinsd")
                    url.set("https://github.com/rybalkinsd")
                }
                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://github.com/rybalkinsd/kohttp/blob/master/LICENSE")
                    }
                }
                scm {
                    url.set("https://github.com/rybalkinsd/kohttp")
                    connection.set("scm:git:git://github.com/rybalkinsd/kohttp.git")
                    developerConnection.set("scm:git:ssh://git@github.com:rybalkinsd/kohttp.git")
                }
                developers {
                    developer {
                        name.set("Sergey")
                    }
                }
            }
        }

        repositories {
            maven {
                credentials {
                    val nexusUsername: String? by project
                    val nexusPassword: String? by project
                    username = nexusUsername
                    password = nexusPassword
                }

                val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            }
        }
    }
}

signing {
    sign(publishing.publications["kohttp"])
}

fun jackson(pack: String) = "com.fasterxml.jackson.$pack"
