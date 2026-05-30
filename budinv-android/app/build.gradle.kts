plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    jacoco
}

android {
    namespace = "com.joragupra.budinv.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.joragupra.budinv.android"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            enableUnitTestCoverage = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    buildFeatures {
        compose = true
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
}

fun budinvVersion(): String {
    val pom = File(rootDir, "../pom.xml")
    val doc = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(pom)
    return doc.getElementsByTagName("version").item(0).textContent
}

dependencies {
    implementation("com.joragupra.budinv:budinv-model:${budinvVersion()}")

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.lifecycle.viewmodel.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)

    implementation(libs.coroutines.android)

    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.robolectric)
    testImplementation(platform(libs.compose.bom))
    testImplementation(libs.compose.ui.test.junit4)

    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}

val coverageExecData: FileTree = fileTree(layout.buildDirectory) {
    include("outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec")
}

val coverageClassDirs: FileTree = fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/debug")) {
    exclude(
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "**/theme/**"
    )
}

val coverageSourceDirs = files("src/main/java")

tasks.register<JacocoReport>("reportCoverage") {
    dependsOn("testDebugUnitTest")

    reports {
        csv.required.set(true)
        xml.required.set(true)
    }

    executionData.setFrom(coverageExecData)
    classDirectories.setFrom(coverageClassDirs)
    sourceDirectories.setFrom(coverageSourceDirs)

    doLast {
        val lines = reports.csv.outputLocation.asFile.get().readLines().drop(1)
        var iMissed = 0L; var iCovered = 0L
        var bMissed = 0L; var bCovered = 0L
        var lMissed = 0L; var lCovered = 0L
        lines.forEach { line ->
            val c = line.split(",")
            if (c.size >= 9) {
                iMissed += c[3].toLong(); iCovered += c[4].toLong()
                bMissed += c[5].toLong(); bCovered += c[6].toLong()
                lMissed += c[7].toLong(); lCovered += c[8].toLong()
            }
        }
        fun pct(covered: Long, missed: Long) =
            if (covered + missed > 0) "%5.1f%%".format(covered * 100.0 / (covered + missed)) else "   n/a"
        println("\n=== Coverage Summary ===")
        println("Instructions: ${pct(iCovered, iMissed)}  ($iCovered/${iCovered + iMissed})")
        println("Branches:     ${pct(bCovered, bMissed)}  ($bCovered/${bCovered + bMissed})")
        println("Lines:        ${pct(lCovered, lMissed)}  ($lCovered/${lCovered + lMissed})")
    }
}

tasks.register<JacocoCoverageVerification>("checkCoverage") {
    dependsOn("reportCoverage")

    violationRules {
        rule {
            limit {
                counter = "LINE"
                minimum = "0.30".toBigDecimal()
            }
        }
    }

    executionData.setFrom(coverageExecData)
    classDirectories.setFrom(coverageClassDirs)
    sourceDirectories.setFrom(coverageSourceDirs)
}

tasks.named("check") {
    dependsOn("checkCoverage")
}
