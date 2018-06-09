package com.legech.android.view.generator.entity

data class SettingEntity(
        // Java or Kotlin
        val classType: String,
        val packageName: String,
        val activityOutputPackage: String,
        val fragmentOutputPackage: String,
        val presenterOutputPackage: String
)
