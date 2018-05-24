package com.legech.android.view.generator.model

import com.google.gson.Gson
import com.legech.android.view.generator.entity.OutputEntity
import com.legech.android.view.generator.extensions.*
import java.io.*

class OutputModel {
    private lateinit var javaActivityStr: String
    private lateinit var javaFragmentStr: String
    private lateinit var layoutStr: String

    private lateinit var outputEntity: OutputEntity

    private fun initBufferedReader(str: String) =
            BufferedReader(InputStreamReader(javaClass.getResourceAsStream(str), "UTF-8"))

    fun initData() {
        val buffers = initBufferedReader("/setting.json")
        val settingJson = buffers.readAll()
        outputEntity = Gson().fromJson<OutputEntity>(settingJson, OutputEntity::class.java)

        initBufferedReader("/TemplateActivity.txt").also {
            javaActivityStr = it.readAll()
            it.close()
        }
        initBufferedReader("/TemplateFragment.txt").also {
            javaFragmentStr = it.readAll()
            it.close()
        }
        initBufferedReader("/Template.xml.txt").also {
            layoutStr = it.readAll()
            it.close()
        }
    }

    fun fileOutputExecute() {
        val setting = outputEntity.settingEntity
        outputEntity.classEntityList.forEach {
            val filePackageName = setting.packageName + "." +
                    if (it.isActivity()) setting.activityOutputPackage else setting.fragmentOutputPackage
            val xmlName = if (it.isActivity()) {
                "activity_"
            } else {
                "fragment_"
            } + it.className.toSnakeCase()

            val outSrcPath = File("out/src/")
            if (!outSrcPath.exists()) {
                outSrcPath.mkdir()
            }
            val outJavaPath = outSrcPath.path + if (it.isActivity()) "/activity/" else "/fragment/"
            File(outJavaPath).apply {
                mkdir()
            }
            val activityFileStr = File("$outJavaPath${it.className}${it.outputType}.${setting.fileExt()}").absolutePath
            val file = File(activityFileStr).apply {
                if (exists()) {
                    delete()
                }
            }
            PrintWriter(BufferedWriter(FileWriter(file))).apply {
                val replaceText = if (it.isActivity()) javaActivityStr else javaFragmentStr
                print(replaceText
                        .replace("\${NAME}", it.className)
                        .replace("\${CLASS_NAME}", it.className + it.outputType)
                        .replace("\${PACKAGE_NAME}", filePackageName)
                        .replace("\${APP_PACKAGE}", setting.packageName)
                        .replace("\${XML_NAME}", xmlName)
                        .replace("\${TITLE}", it.title))
                close()
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
