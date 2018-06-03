package com.legech.android.view.generator.extensions

import com.legech.android.view.generator.entity.ClassEntity

fun ClassEntity.isActivity() = this.outputType == "Activity"

fun ClassEntity.isFragment() = this.outputType == "Fragment"