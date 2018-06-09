package com.legech.android.view.generator.entity

import com.google.gson.annotations.SerializedName

data class LinkEntity(
        @SerializedName("title")
        val title: String,
        @SerializedName("class_name")
        val className: String
)
