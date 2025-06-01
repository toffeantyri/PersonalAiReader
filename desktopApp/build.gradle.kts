import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    implementation(project(":shared"))
    implementation(compose.desktop.currentOs)
}

kotlin {
    jvmToolchain(17)
}


compose {
    desktop {
        application {
            mainClass = "MainKt"
            jvmArgs += listOf(
                "--add-opens=java.base/sun.misc=ALL-UNNAMED",
                "--add-opens=java.base/java.lang=ALL-UNNAMED"
            )

            nativeDistributions {
                modules("jdk.unsupported") // Добавляем модуль с Unsafe
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageName = "AiReader"
                packageVersion = "1.0.0"
                macOS {
                    bundleID = "personal.ai.reader"
                    entitlementsFile.set(project.file("entitlements.plist"))
                }
                windows {
//                    iconFile.set(File("shared/src/jvmMain/kotlin/resources/ic_launcher.webp"))
                }
            }
        }
    }
}