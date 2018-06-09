package com.legech.android.view.generator.extensions

import com.legech.android.view.generator.entity.SettingEntity

fun SettingEntity.fileExt() = if (this.classType == "Java") {
    "java"
} else {
    "kt"
}

fun SettingEntity.outputManifest(className: String) {
    println("<activity android:name=\".${this.activityOutputPackage}.${className}Activity\"\n" +
            "android:exported=\"false\"\n" +
            " android:screenOrientation=\"portrait\"/>")
}