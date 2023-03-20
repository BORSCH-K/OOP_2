plugins {
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.serialization") version "1.8.0"
}
repositories {
    mavenCentral()
}
val serializationVersion = "1.5.0-RC"
val kotestVersion = "5.4.1"
dependencies {
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    implementation("org.scalaz:scalaz-deriving-jsonformat_2.12:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.3")
}
tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}