package com.legech.android.view.generator.model

import com.legech.android.view.generator.entity.SettingEntity
import com.legech.android.view.generator.entity.UiClassEntity
import com.legech.android.view.generator.extensions.bufferedString
import com.legech.android.view.generator.extensions.outputManifest
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

class ActivityOutputModel(uiClassEntity: UiClassEntity, settingEntity: SettingEntity) : UiOutputModel(uiClassEntity, settingEntity) {

    override fun templateStr() = bufferedString("/TemplateActivity.txt")

    override fun uiOutput(file: File) {
        val links = if (uiClassEntity.activityLinks != null && uiClassEntity.activityLinks.isNotEmpty()) {
            val buffer = StringBuffer()
            buffer.append(bufferedString("/TemplateActivityLinkHeader.txt") + "\n")
            uiClassEntity.activityLinks.forEach {
                buffer.append(bufferedString("/TemplateActivityLink.txt")
                        .replace("\${TITLE}", it.title)
                        .replace("\${CLASS_NAME}", it.className + uiClassEntity.outputType)
                )
                buffer.append("\n")
            }
            buffer.toString()
        } else {
            ""
        }
        PrintWriter(BufferedWriter(FileWriter(file))).apply {
            print(templateStr()
                    .replace("\${NAME}", uiClassEntity.className)
                    .replace("\${CLASS_NAME}", uiClassEntity.className + uiClassEntity.outputType)
                    .replace("\${PACKAGE_NAME}", settingEntity.packageName)
                    .replace("\${OUTPUT_PACKAGE}", settingEntity.activityOutputPackage)
                    .replace("\${XML_NAME}", xmlName)
                    .replace("\${TITLE}", uiClassEntity.title)
                    .replace("\${ACTIVITY_LINKS}", links)
            )
            close()
        }
        settingEntity.outputManifest(uiClassEntity.className)
    }
}