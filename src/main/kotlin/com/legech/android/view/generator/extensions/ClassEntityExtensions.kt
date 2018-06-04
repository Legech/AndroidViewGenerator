package com.legech.android.view.generator.extensions

import com.legech.android.view.generator.entity.UiClassEntity

fun UiClassEntity.isActivity() = this.outputType == "Activity"

fun UiClassEntity.isFragment() = this.outputType == "Fragment"