package com.legech.android.view.generator.entity

import com.google.gson.annotations.SerializedName

data class UiClassEntity(
        val title: String,
        val outputType: String,
        val className: String,
        val outPresenter: Boolean,
        val activityLinks: List<LinkEntity>?
)
