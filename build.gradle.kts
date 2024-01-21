plugins {
    kotlin("multiplatform") version "1.8.0"
    id("maven-publish")
    id("signing")
}

group = "dev.sitar"
version = "0.1"

val sonatypeUsername: String? = System.getenv("SONATYPE_USERNAME")
val sonatypePassword: String? = System.getenv("SONATYPE_PASSWORD")

repositories {
    mavenCentral()
}

val javadocJar = tasks.register("javadocJar", Jar::class.java) {
    archiveClassifier.set("javadoc")
}

// https://github.com/gradle/gradle/issues/26091
val signingTasks = tasks.withType<Sign>()
tasks.withType<AbstractPublishToMaven>().configureEach {
    dependsOn(signingTasks)
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

publishing {
    publications {
        repositories {
            maven {
                name="oss"
                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

                credentials {
                    username = sonatypeUsername
                    password = sonatypePassword
                }
            }
        }

        withType<MavenPublication> {
            artifact(javadocJar)

            pom {
                name.set("keystone")
                description.set("A no-dependency Kotlin multiplatform library for pipelines.")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                url.set("https://github.com/lost-illusi0n/keystone")

                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/lost-illusi0n/keystone/issues")
                }

                scm {
                    connection.set("https://github.com/lost-illusi0n/keystone.git")
                    url.set("https://github.com/lost-illusi0n/keystone.git")
                }

                developers {
                    developer {
                        name.set("Marco Sitar")
                        email.set("marco+oss@sitar.dev")
                    }
                }
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}