package com.legech.android.view.generator.entity

import com.google.gson.annotations.SerializedName

data class SettingEntity(
        // Java or Kotlin
        @SerializedName("class_type")
        val classType: String,
        @SerializedName("package_name")
        val packageName: String,
        @SerializedName("activity_output_package")
        val activityOutputPackage: String,
        @SerializedName("fragment_output_package")
        val fragmentOutputPackage: String
)
