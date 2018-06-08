package com.legech.android.view.generator.entity

import com.google.gson.annotations.SerializedName

data class OutputEntity (
        @SerializedName("setting")
        val settingEntity: SettingEntity,
        @SerializedName("ui_classes")
        val uiUiClassEntityList: List<UiClassEntity>
)
