package com.legech.android.view.generator.model

import com.legech.android.view.generator.entity.SettingEntity
import com.legech.android.view.generator.entity.UiClassEntity
import com.legech.android.view.generator.extensions.bufferedString
import com.legech.android.view.generator.extensions.fileExt
import com.legech.android.view.generator.extensions.toSnakeCase
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

abstract class UiOutputModel(val uiClassEntity: UiClassEntity, val settingEntity: SettingEntity) {

    abstract fun templateStr(): String

    abstract fun uiOutput(file: File)

    val xmlName = uiClassEntity.outputType.toLowerCase() + "_" + uiClassEntity.className.toSnakeCase()

    private val presenterStr = bufferedString("/TemplatePresenter.txt")

    private val layoutStr = bufferedString("/TemplateLayout.txt")

    fun outputPresenter(file: File) {
        if (uiClassEntity.outPresenter) {
            val outPresenterPath = file.path + "/presenter/"
            File(outPresenterPath).apply {
                mkdir()
            }

            val presenterFileStr = File("$outPresenterPath${uiClassEntity.className}Presenter.${settingEntity.fileExt()}").absolutePath
            val presenterFile = File(presenterFileStr).apply {
                if (exists()) {
                    delete()
                }
            }
            PrintWriter(BufferedWriter(FileWriter(presenterFile))).apply {
                print(presenterStr
                        .replace("\${NAME}", uiClassEntity.className)
                        .replace("\${CLASS_NAME}", uiClassEntity.className + uiClassEntity.outputType)
                        .replace("\${PACKAGE_NAME}", settingEntity.packageName)
                        .replace("\${OUTPUT_PACKAGE}", settingEntity.presenterOutputPackage)
                        .replace("\${TITLE}", uiClassEntity.title))
                close()
            }
        }
    }

    fun outputLayout() {
        val layoutPath = "out/src/layout/"
        File(layoutPath).mkdir()

        File(File("$layoutPath$xmlName.xml").absolutePath).also {
            if (it.exists()) {
                it.delete()
            }
            PrintWriter(BufferedWriter(FileWriter(it))).apply {
                print(layoutStr)
                close()
            }
        }
    }
}
