// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id ("com.google.dagger.hilt.android") version ("2.51.1") apply false
    alias(libs.plugins.google.gms.google.services) apply false
    id("org.sonarqube") version "6.0.1.5171"
//    id("com.google.devtools.ksp") version "2.0.21-1.0.27"

}
sonarqube {
    properties {
        property("sonar.projectKey", "MTprogramer_Singkee")
        property("sonar.organization", "mtprogramer")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
