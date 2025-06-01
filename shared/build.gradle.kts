import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.konan.target.Family

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.room)
}

kotlin {
    jvmToolchain(17)


    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

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
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.ktor.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)

                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)
                implementation(libs.sqlite)

                api(libs.decompose.decompose)
                api(libs.decompose.extensionsComposeJetbrains)
                api(libs.essenty.lifecycle)

            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.androidx.data.store.preferences)
//                ksp(libs.room.compiler)
            }
        }

        iosMain {
            kotlin.srcDir("build/generated/ksp/metadata")

            dependencies {

                implementation(libs.ktor.client.darwin)
//                ksp(libs.room.compiler)
            }
        }

        val jvmMain by getting {
            dependencies {
//                implementation(libs.ktor.client.cio)
//                ksp(libs.room.compiler)
            }
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "com.personalai.reader.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}

