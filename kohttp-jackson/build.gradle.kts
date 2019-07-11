dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api(project(":kohttp-core"))

    val jacksonVersion = "2.9.9"
    api(jackson("core"), "jackson-databind", jacksonVersion)
    implementation(jackson("module"), "jackson-module-kotlin", jacksonVersion)

    testImplementation(kotlin("test-junit"))
    testImplementation("org.assertj:assertj-core:3.12.2")

}

fun jackson(pack: String) = "com.fasterxml.jackson.$pack"
