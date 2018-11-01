import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.Coroutines

val publish = false

plugins {
    kotlin("jvm") version "1.3.0"
    java

    jacoco

    id("org.jetbrains.dokka") version "0.9.16"
    maven
    `maven-publish`
    signing
}

group = "io.github.rybalkinsd"
version = "0.4.0"

repositories {
    mavenCentral()
}

fun jackson(pack: String) = "com.fasterxml.jackson.$pack"

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.0.0")

    val jacksonVersion = "2.9.7"
    compile(jackson("core"), "jackson-databind", jacksonVersion)
    compile(jackson("dataformat"), "jackson-dataformat-yaml", jacksonVersion)
    compile(jackson("module"), "jackson-module-kotlin", jacksonVersion)
    compile("com.squareup.okhttp3", "okhttp", "3.11.0")
    
    testCompile(kotlin("test-junit"))
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

val sourcesJar by tasks.creating(Jar::class) {
    classifier = "sources"
    from(java.sourceSets["main"].allSource)
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    classifier = "javadoc"

    val dokka by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
    }

    from(dokka)
}

val nexusUsername by project
val nexusPassword by project

tasks {
    "uploadArchives"(Upload::class) {
        repositories {

            withConvention(MavenRepositoryHandlerConvention::class) {
                publishing {
                    (publications) {
                        "mavenSources"(MavenPublication::class) {
                            from(components["java"])
                            artifact(dokkaJar)
                            artifact(sourcesJar)
                        }
                    }
                }

                mavenDeployer {
                    withGroovyBuilder {
                        if (publish) {
                            "repository"("url" to uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")) {
                                "authentication"("userName" to nexusUsername, "password" to nexusPassword)
                            }
                        } else {
                            "repository"("url" to uri("$buildDir/pub/"))
                        }
                        "snapshotRepository"("url" to uri("https://oss.sonatype.org/content/repositories/snapshots/")) {
                            "authentication"("userName" to nexusUsername, "password" to nexusPassword)
                        }
                        "beforeDeployment" {
                            if (signing.isRequired)
                                signing.signPom(delegate as MavenDeployment)
                        }
                    }

                    pom.project {
                        withGroovyBuilder {

                            "description"("Kotlin http dsl based on okhttp")
                            "name"("Kotlin http dsl")
                            "url"("https://github.com/rybalkinsd/kohttp")
                            "organization" {
                                "name"("io.github.rybalkinsd")
                                "url"("https://github.com/rybalkinsd")
                            }
                            "licenses" {
                                "license" {
                                    "name"("Apache License 2.0")
                                    "url"("https://github.com/mautini/schemaorg-java/blob/master/LICENSE")
                                    "distribution"("repo")
                                }
                            }
                            "scm" {
                                "url"("https://github.com/rybalkinsd/kohttp")
                                "connection"("scm:git:git://github.com/rybalkinsd/kohttp.git")
                                "developerConnection"("scm:git:ssh://git@github.com:rybalkinsd/kohttp.git")
                            }
                            "developers" {
                                "developer" {
                                    "name"("Sergey")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

signing {
    isRequired = publish
    sign(configurations.archives)
}

artifacts {
    withGroovyBuilder {
        "archives"(tasks["jar"], sourcesJar, dokkaJar)
    }
}
