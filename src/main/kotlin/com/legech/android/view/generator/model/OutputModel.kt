package com.legech.android.view.generator.model

import com.legech.android.view.generator.extensions.*
import java.io.*

class OutputModel {
    private val activityStr by lazy {
        bufferedString("/TemplateActivity.txt")
    }

    private val fragmentStr by lazy {
        bufferedString("/TemplateFragment.txt")
    }

    private val presenterStr by lazy {
        bufferedString("/TemplatePresenter.txt")
    }

    private val layoutStr by lazy {
        bufferedString("/TemplateLayout.txt")
    }

    private val outputEntity by lazy {
        createOutputEntity("/setting.json")
    }

    fun fileOutputExecute() {
        val setting = outputEntity.setting
        outputEntity.uiClasses.forEach {
            val xmlName = it.outputType.toLowerCase() + "_" + it.className.toSnakeCase()

            val outSrcPath = File("out/src/")
            if (!outSrcPath.exists()) {
                outSrcPath.mkdir()
            }
            val outPath = outSrcPath.path + "/" + it.outputType.toLowerCase() + "/"
            File(outPath).apply {
                mkdir()
            }
            val fileStr = File("$outPath${it.className}${it.outputType}.${setting.fileExt()}").absolutePath
            val uiFile = File(fileStr).apply {
                if (exists()) {
                    delete()
                }
            }
            PrintWriter(BufferedWriter(FileWriter(uiFile))).apply {
                val replaceText = if (it.isActivity()) activityStr else fragmentStr
                print(replaceText
                        .replace("\${NAME}", it.className)
                        .replace("\${CLASS_NAME}", it.className + it.outputType)
                        .replace("\${PACKAGE_NAME}", setting.packageName)
                        .replace("\${OUTPUT_PACKAGE}", if (it.isActivity()) setting.activityOutputPackage else setting.fragmentOutputPackage)
                        .replace("\${XML_NAME}", xmlName)
                        .replace("\${TITLE}", it.title))
                close()
            }
            // Presenter
            val outPresenterPath = outSrcPath.path + "/presenter/"
            File(outPresenterPath).apply {
                mkdir()
            }
            if (it.outPresenter) {
                val presenterFileStr = File("$outPresenterPath${it.className}Presenter.${setting.fileExt()}").absolutePath
                val presenterFile = File(presenterFileStr).apply {
                    if (exists()) {
                        delete()
                    }
                }
                PrintWriter(BufferedWriter(FileWriter(presenterFile))).apply {
                    print(presenterStr
                            .replace("\${NAME}", it.className)
                            .replace("\${CLASS_NAME}", it.className + it.outputType)
                            .replace("\${PACKAGE_NAME}", setting.packageName)
                            .replace("\${OUTPUT_PACKAGE}", setting.presenterOutputPackage)
                            .replace("\${TITLE}", it.title))
                    close()
                }
            }
            // Layout Output
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
            // OutPutComment
            if (it.isActivity()) {
                setting.outputManifest(it.className)
            }
        }
    }
}

