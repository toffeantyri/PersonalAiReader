import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.konan.target.Family

plugins {
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.multiplatform)
}

kotlin {
    iosX64()
    iosArm64()

    targets
        .filterIsInstance<KotlinNativeTarget>()
        .filter { it.konanTarget.family == Family.IOS }
        .forEach {
            it.binaries.framework(listOf(NativeBuildType.DEBUG)) {
                binaryOption("bundleId", "ai.personal.reader")
                baseName = "shared"
                isStatic = true
                export(libs.decompose.decompose)
                export(libs.decompose.extensionsComposeJetbrains)
                export(libs.essenty.lifecycle)
            }
        }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
            }
        }
    }
} 