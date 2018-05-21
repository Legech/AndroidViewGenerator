package com.legech.android.view.generator.model

import com.google.gson.Gson
import com.legech.android.view.generator.entity.OutputEntity
import com.legech.android.view.generator.extensions.readAll
import com.legech.android.view.generator.extensions.toSnakeCase
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

        getActivityFileStr()
        getFragmentFileStr()
        setLayoutFileStr()
    }

    private fun getActivityFileStr() {
        val buffer = initBufferedReader("/TemplateActivity.txt")
        javaActivityStr = buffer.readAll()
        buffer.close()
    }

    private fun getFragmentFileStr() {
        val buffer = initBufferedReader("/TemplateFragment.txt")
        javaFragmentStr = buffer.readAll()
        buffer.close()
    }

    private fun setLayoutFileStr() {
        val buffer = initBufferedReader("/Template.xml.txt")
        layoutStr = buffer.readAll()
        buffer.close()
    }

    fun fileOutputExecute() {

    }

    fun controllerFileReplace() {
        val br = initBufferedReader("/class.csv")

        br.lines().forEach {
            val csvList = it.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (csvList[0] != "Number") {
                val type = csvList[1]
                val name = csvList[2]
                val appPackage = csvList[3]
                val filePackageName = csvList[3] + "" + csvList[4]
                val viewType = csvList[5] // Activity or Fragment
                val title = csvList[6]
                val xmlName = if (viewType == "Activity") {
                    "activity_"
                } else {
                    "fragment_"
                } + csvList[2].toSnakeCase()

                val temp =  javaActivityStr
                            .replace("\${NAME}", name)
                            .replace("\${CLASS_NAME}", name + viewType)
                            .replace("\${PACKAGE_NAME}", filePackageName)
                            .replace("\${APP_PACKAGE}", appPackage)
                            .replace("\${XML_NAME}", xmlName)
                            .replace("\${TITLE}", "$title $viewType.")

                val outSrcPath = File("out/src/")
                if (!outSrcPath.exists()) {
                    outSrcPath.mkdir()
                }
                val outJavaPath = outSrcPath.path + if (viewType == "Activity") {
                    "/activity/"
                } else {
                    "/fragment/"
                }
                val newDir = File(outJavaPath)
                newDir.mkdir()

                val ext = if (type == "Kotlin") "kt" else "java"
                val activityFileStr = File("$outJavaPath$name$viewType.$ext").absolutePath
                val file = File(activityFileStr)
                if (file.exists()) {
                    file.delete()
                }

                val javaPw = PrintWriter(BufferedWriter(FileWriter(file)))
                javaPw.print(temp)
                javaPw.close()

                // Layout Output
                val outLayoutPath = "out/src/layout/"
                val newLayoutDir = File(outLayoutPath)
                newLayoutDir.mkdir()

                val fileLayoutStr = File("$outLayoutPath$xmlName.xml").absolutePath
                val layoutFile = File(fileLayoutStr)
                if (layoutFile.exists()) {
                    layoutFile.delete()
                }

                val layoutWp = PrintWriter(BufferedWriter(FileWriter(layoutFile)))
                layoutWp.print(layoutStr)
                layoutWp.close()

                // OutPutComment
                if (viewType == "Activity") {
                    println("<activity android:name=\".${csvList[4]}.${name}Activity\"\n" +
                            "android:exported=\"false\"\n" +
                            " android:screenOrientation=\"portrait\"/>")
                }
            }
        }

        br.close()
    }
}
