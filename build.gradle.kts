import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"

    jacoco
    id("org.jetbrains.dokka") version "0.9.16"
    `maven-publish`
    signing
}

allprojects {
    apply(plugin = "jacoco")

    group = "io.github.rybalkinsd"
    version = "0.12.0"

    repositories {
        mavenCentral()
    }
}

val notToPublish = listOf("kohttp-test")

subprojects {
    apply(plugin = "kotlin")

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    val sourceSets = the<SourceSetContainer>()

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/main/kotlin")
    }

    if (project.name !in notToPublish) {
        apply(plugin = "org.jetbrains.dokka")
        apply(plugin = "maven-publish")
        apply(plugin = "signing")

        val sourcesJar = task<Jar>("sourcesJar") {
            from(sourceSets["main"].allSource)
            archiveClassifier.set("sources")
        }

        val dokkaJar = task<Jar>("dokkaJar") {
            group = JavaBasePlugin.DOCUMENTATION_GROUP
            archiveClassifier.set("javadoc")
        }

        publishing {
            publications {
                create<MavenPublication>(project.name) {
                    from(components["java"])
                    artifacts {
                        artifact(sourcesJar)
                        artifact(dokkaJar)
                    }

                    pom {
                        name.set("Kotlin DSL http client")
                        description.set("Kotlin DSL http client")
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
            sign(publishing.publications[project.name])
        }
    }

}


tasks.withType<JacocoReport> {
    val containers = subprojects.map { it.the<SourceSetContainer>()["main"] }

    val output = containers.flatMap { it.output }
    val sources = containers.flatMap { it.allSource.srcDirs }

    val exec = subprojects.flatMap { it.tasks }
        .filterIsInstance<Test>()
        .flatMap { files(it) }
        .filter { it.exists() && it.name.endsWith(".exec") }

    additionalSourceDirs.setFrom(sources)
    sourceDirectories.setFrom(sources)
    classDirectories.setFrom(output)
    executionData.setFrom(exec)

    reports {
        xml.isEnabled = true
        xml.destination = File("$buildDir/reports/jacoco/report.xml")
        html.isEnabled = false
    }
}
