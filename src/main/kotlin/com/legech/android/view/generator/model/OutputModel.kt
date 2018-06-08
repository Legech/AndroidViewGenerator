package com.legech.android.view.generator.model

import com.google.gson.Gson
import com.legech.android.view.generator.entity.OutputEntity
import com.legech.android.view.generator.extensions.*
import java.io.*

class OutputModel {
    private lateinit var activityStr: String
    private lateinit var fragmentStr: String
    private lateinit var presenterStr: String
    private lateinit var layoutStr: String

    private lateinit var outputEntity: OutputEntity

    private fun initBufferedReader(str: String) =
            BufferedReader(InputStreamReader(javaClass.getResourceAsStream(str), "UTF-8"))

    fun initData() {
        val buffers = initBufferedReader("/setting.json")
        val settingJson = buffers.readAll()
        outputEntity = Gson().fromJson<OutputEntity>(settingJson, OutputEntity::class.java)

        initBufferedReader("/TemplateActivity.txt").also {
            activityStr = it.readAll()
            it.close()
        }
        initBufferedReader("/TemplateFragment.txt").also {
            fragmentStr = it.readAll()
            it.close()
        }
        initBufferedReader("/TemplatePresenter.txt").also {
            presenterStr = it.readAll()
            it.close()
        }
        initBufferedReader("/Template.xml.txt").also {
            layoutStr = it.readAll()
            it.close()
        }
    }

    fun fileOutputExecute() {
        val setting = outputEntity.settingEntity
        outputEntity.uiUiClassEntityList.forEach {
            val xmlName = it.outputType.toLowerCase() + "_" + it.className.toSnakeCase()

            val outSrcPath = File("out/src/")
            if (!outSrcPath.exists()) {
                outSrcPath.mkdir()
            }
            val outPath = outSrcPath.path + "/" + it.outputType.toLowerCase() + "/"
            File(outPath).apply {
                mkdir()
            }
            val outPresenterPath = outSrcPath.path + "/presenter/"
            File(outPresenterPath).apply {
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
