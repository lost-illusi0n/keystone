plugins {
    kotlin("multiplatform") version "1.8.0"
}

group = "dev.sitar"
version = "0.1"

repositories {
    mavenCentral()
}

kotlin {
    explicitApi()

    jvm {
        jvmToolchain(11)
    }
    js(BOTH) {
        browser()
        nodejs()
    }
    mingwX64()
    linuxX64()

    sourceSets {
        val commonMain by getting
    }
}
