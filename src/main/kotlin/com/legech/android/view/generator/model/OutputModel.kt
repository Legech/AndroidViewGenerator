package com.legech.android.view.generator.model

import com.legech.android.view.generator.extensions.createOutputEntity
import com.legech.android.view.generator.extensions.fileExt
import com.legech.android.view.generator.extensions.isActivity
import java.io.File

class OutputModel {

    fun fileOutputExecute() {
        val outputEntity = createOutputEntity("/setting.json")
        val setting = outputEntity.setting
        outputEntity.uiClasses.forEach {
            val outSrcFile = File("out/src/").apply { if (!exists()) mkdir() }

            val model = if (it.isActivity()) ActivityOutputModel(it, setting) else FragmentOutputModel(it, setting)
            model.apply {
                val outPath = outSrcFile.path + "/" + it.outputType.toLowerCase() + "/"
                File(outPath).apply { mkdir() }
                val fileStr = File("$outPath${it.className}${it.outputType}.${setting.fileExt()}").absolutePath
                val uiFile = File(fileStr).apply {
                    if (exists()) delete()
                }
                uiOutput(uiFile)
                outputPresenter(outSrcFile)
                outputLayout()
            }
        }
    }
}