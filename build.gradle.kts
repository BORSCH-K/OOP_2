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
    implementation("org.scalaz:scalaz-deriving-jsonformat_2.12:1.0.0")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
}
tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}