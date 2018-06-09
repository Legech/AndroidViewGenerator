package com.legech.android.view.generator.model

import com.legech.android.view.generator.entity.SettingEntity
import com.legech.android.view.generator.entity.UiClassEntity
import com.legech.android.view.generator.extensions.bufferedString
import com.legech.android.view.generator.extensions.isActivity
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

class FragmentOutputModel(uiClassEntity: UiClassEntity, settingEntity: SettingEntity) : UiOutputModel(uiClassEntity, settingEntity) {

    override fun templateStr() =
            bufferedString("/TemplateFragment.txt")

    override fun uiOutput(file: File) {
        PrintWriter(BufferedWriter(FileWriter(file))).apply {
            print(templateStr()
                    .replace("\${NAME}", uiClassEntity.className)
                    .replace("\${CLASS_NAME}", uiClassEntity.className + uiClassEntity.outputType)
                    .replace("\${PACKAGE_NAME}", settingEntity.packageName)
                    .replace("\${OUTPUT_PACKAGE}", settingEntity.fragmentOutputPackage)
                    .replace("\${XML_NAME}", xmlName)
                    .replace("\${TITLE}", uiClassEntity.title))
            close()
        }
    }


}