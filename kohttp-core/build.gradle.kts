dependencies {
    implementation(kotlin("stdlib-jdk8"))

    api("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.2.1")
    api("com.squareup.okhttp3", "okhttp", "3.14.2")


    testImplementation(kotlin("test-junit"))
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.mock-server:mockserver-netty:5.5.4")
    testImplementation("org.mock-server:mockserver-client-java:5.5.4")
    testImplementation("org.assertj:assertj-core:3.12.2")
}
