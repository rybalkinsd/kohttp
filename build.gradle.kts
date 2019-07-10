import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.40" apply false
    jacoco

    id("org.jetbrains.dokka") version "0.9.16"
    `maven-publish`
    signing

}

allprojects {
    group = "io.github.rybalkinsd"
    version = "0.11.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

//    val sourcesJar = task<Jar>("sourcesJar") {
//        from(sourceSets.main.get().allJava)
//        classifier = "sources"
//    }

//    publishing {
//        publications {
//            create<MavenPublication>("kohttp") {
//                from(components["java"])
//                artifacts {
//                    artifact(sourcesJar)
//                    artifact(dokkaJar)
//                }
//
//                pom {
//                    name.set("Kotlin http dsl")
//                    description.set("Kotlin http dsl based on okhttp")
//                    url.set("https://github.com/rybalkinsd/kohttp")
//                    organization {
//                        name.set("io.github.rybalkinsd")
//                        url.set("https://github.com/rybalkinsd")
//                    }
//                    licenses {
//                        license {
//                            name.set("Apache License 2.0")
//                            url.set("https://github.com/rybalkinsd/kohttp/blob/master/LICENSE")
//                        }
//                    }
//                    scm {
//                        url.set("https://github.com/rybalkinsd/kohttp")
//                        connection.set("scm:git:git://github.com/rybalkinsd/kohttp.git")
//                        developerConnection.set("scm:git:ssh://git@github.com:rybalkinsd/kohttp.git")
//                    }
//                    developers {
//                        developer {
//                            name.set("Sergey")
//                        }
//                    }
//                }
//            }
//
//            repositories {
//                maven {
//                    credentials {
//                        val nexusUsername: String? by project
//                        val nexusPassword: String? by project
//                        username = nexusUsername
//                        password = nexusPassword
//                    }
//
//                    val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
//                    val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")
//                    url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
//                }
//            }
//        }
//    }
//
//    signing {
//        sign(publishing.publications["kohttp"])
//    }


}





tasks.withType<JacocoReport> {
    reports {
        xml.isEnabled = true
        xml.destination = File("$buildDir/reports/jacoco/report.xml")
        html.isEnabled = false
    }
}


