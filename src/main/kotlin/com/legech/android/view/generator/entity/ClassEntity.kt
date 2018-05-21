package com.legech.android.view.generator.entity

import com.google.gson.annotations.SerializedName

data class ClassEntity(
        @SerializedName("title")
        val title: String,
        @SerializedName("output_type")
        val outputType: String,
        @SerializedName("class_name")
        val className: String
)
