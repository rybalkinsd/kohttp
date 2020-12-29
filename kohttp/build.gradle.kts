dependencies {
    implementation(kotlin("stdlib-jdk8"))

    api("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.2.1")
    api("com.squareup.okhttp3", "okhttp", "4.9.0")


    testImplementation(kotlin("test-junit"))
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.mock-server:mockserver-netty:5.5.4")
    testImplementation("org.mock-server:mockserver-client-java:5.5.4")
    testImplementation("org.assertj:assertj-core:3.16.1")
    testImplementation("io.github.hakky54:sslcontext-kickstart:3.0.9")
}