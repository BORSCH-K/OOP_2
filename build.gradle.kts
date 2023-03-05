plugins {
    kotlin ("jvm") version "1.7.10"
}
repositories {
    mavenCentral()
}
val kotestVersion = "5.4.1"
dependencies {
    testImplementation(
            "io.kotest:kotest-runner-junit5-jvm:$kotestVersion"
    )
}
tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}