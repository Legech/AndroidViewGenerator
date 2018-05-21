package com.legech.android.view.generator.entity

import com.google.gson.annotations.SerializedName

data class OutputEntity (
        @SerializedName("setting")
        val settingEntity: SettingEntity,
        @SerializedName("classes")
        val classEntityList: List<ClassEntity>
)
