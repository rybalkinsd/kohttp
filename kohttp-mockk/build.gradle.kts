dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api(project(":kohttp"))

    implementation("io.mockk:mockk:1.9.3")

    testImplementation(kotlin("test-junit"))
    testImplementation("org.assertj:assertj-core:3.12.2")
}
