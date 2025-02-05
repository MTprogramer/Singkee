// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id ("com.google.dagger.hilt.android") version ("2.51.1") apply false
    alias(libs.plugins.google.gms.google.services) apply false
    id("org.sonarqube") version "6.0.1.5171"
}
sonarqube {
    properties {
        property("sonar.projectKey", "mtprogramer_Singkee")
        property("sonar.organization", "MTprogramer_Singkee")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
