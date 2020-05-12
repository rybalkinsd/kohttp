dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api(project(":kohttp"))

    val jacksonVersion = "2.9.9"
    api(jackson("core"), "jackson-databind", jacksonVersion)
    implementation(jackson("module"), "jackson-module-kotlin", jacksonVersion)

    testImplementation(kotlin("test-junit"))
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.mock-server:mockserver-netty:5.5.4")
    testImplementation("org.mock-server:mockserver-client-java:5.5.4")

    testImplementation("org.assertj:assertj-core:3.16.1")
}

fun jackson(pack: String) = "com.fasterxml.jackson.$pack"
