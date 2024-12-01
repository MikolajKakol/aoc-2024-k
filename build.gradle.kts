plugins {
    kotlin("jvm") version "2.1.0"
}

sourceSets {
    test {
        kotlin.srcDir("src")
    }
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
}
