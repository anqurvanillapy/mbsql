plugins {
    java
    antlr
    id("io.freefair.lombok") version "5.0.0-rc4"
}

group = "io.anqur"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit", "junit", "4.12")
    implementation("org.jetbrains:annotations:15.0")
    antlr("org.antlr:antlr4:4.8")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

tasks.withType<JavaCompile> {
    options.compilerArgs = listOf(
            "--add-exports",
            "jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED",

            "--add-exports",
            "jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED"
    )
}
