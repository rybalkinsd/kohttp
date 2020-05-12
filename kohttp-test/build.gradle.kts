dependencies {
    testImplementation(kotlin("stdlib-jdk8"))
    testImplementation(project(":kohttp"))
    testImplementation(project(":kohttp-jackson"))

    testImplementation(kotlin("test-junit"))
    testImplementation("org.assertj:assertj-core:3.16.1")
}
