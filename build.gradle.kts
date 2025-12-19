import org.codehaus.groovy.tools.shell.util.Logger.io

plugins {
    kotlin("jvm") version "2.2.21"
    id("com.diffplug.spotless") version "8.1.0"
}

group = "com.rojiani"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

val kotestVersion = "6.0.0.M1"
val multikVersion = "0.2.3"
val junitVersion = "6.1.0-M1"
val ojAlgoVersion = "56.1.1"

dependencies {
    implementation("org.ojalgo:ojalgo:$ojAlgoVersion")
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("io.kotest:kotest-assertions-shared:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}

kotlin {
    jvmToolchain(23)
}

tasks.test {
    useJUnitPlatform()
}

spotless { kotlin { ktfmt("0.59").googleStyle() } }