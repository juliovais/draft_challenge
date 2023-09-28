// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}
//@TODO:criar variável pro dagger hilt e usar nos 2 gradles
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0")
    }
}