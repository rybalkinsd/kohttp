plugins { 
    id("kotlin-kapt")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api(project(":kohttp"))
    implementation("com.squareup.moshi:moshi:1.9.2")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.9.2")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation(kotlin("test-junit"))
    testImplementation("org.assertj:assertj-core:3.16.1")
}
