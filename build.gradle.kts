plugins {
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.serialization") version "1.8.0"
}
repositories {
    mavenCentral()
}
val serializationVersion = "1.5.0-RC"
val jacksonVersion = "2.14.2"
val kotestVersion = "5.5.5"
dependencies{
    testImplementation(
        "io.kotest:kotest-runner-junit5-jvm:$kotestVersion")}
tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
repositories {
    mavenCentral()
}
dependencies {
//    testImplementation(kotlin("test"));
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.mongodb:mongodb-driver-sync:4.3.+")
//    implementation("io.ktor:ktor-server-netty:2.2.4")
//    implementation("io.ktor:ktor-server-html-builder:2.2.4")
//    implementation("io.ktor:ktor-server-freemarker:2.2.4")
    implementation("org.litote.kmongo:kmongo-serialization:4.8.0")
    implementation("org.litote.kmongo:kmongo-id-serialization:4.8.0")
    implementation("org.json:json:20230227")
//    implementation("io.arrow-kt:arrow-core:1.1.2")
//    implementation("org.apache.xmlgraphics:batik-xml:1.14")
//    implementation("io.ktor:ktor-client-core:1.6.0")
//    implementation("io.ktor:ktor-client-cio:1.6.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
//    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")
//    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
//    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
//    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.9.8")
//    implementation("org.jsoup:jsoup:1.14.3")
}
dependencies {
    implementation("com.google.code.gson:gson:2.8.8")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
dependencies {
    implementation("jaxen:jaxen:1.2.0")
}

//для курсовой
//dependencies {
//    implementation("org.apache.poi:poi:5.2.2")
//    implementation("org.apache.poi:poi-ooxml:5.2.2")
//}
//dependencies {
//    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
//    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
//}
dependencies {
    implementation("org.mongodb:mongodb-driver-sync:4.4.2")
}
