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
                binaryOption("bundleId", "notebook.ai.reader")
                baseName = "shared"
                isStatic = true

                //decompose
                export(libs.decompose.decompose)
                export(libs.decompose.extensionsComposeJetbrains)

                export(libs.essenty.lifecycle)


                // Optional, only if you need state preservation on Darwin (Apple) targets
                //export("com.arkivanov.essenty:state-keeper:<essenty_version>")

                // Optional, only if you need state preservation on Darwin (Apple) targets
                //export("com.arkivanov.parcelize.darwin:runtime:<parcelize_darwin_version>")
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