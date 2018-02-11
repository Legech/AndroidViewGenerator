import java.io.*
import java.io.File



class FileOutput {

    @Throws(IOException::class)
    fun getJavaFileStr(): String {
        val inputFile = "src/template/Template.java.txt"
        val isr = InputStreamReader(FileInputStream(File(inputFile).absolutePath))
        val br = BufferedReader(isr)
        val stringBuffer = StringBuffer()

        for (msg in br.lines()) {
            stringBuffer.append(msg)
        }

        br.close()
        return stringBuffer.toString()
    }


    @Throws(IOException::class)
    fun getXmlFileStr(): String {
        val inputFile = "src/template/Template.xml.txt"
        val isr = InputStreamReader(FileInputStream(File(inputFile).absolutePath))
        val br = BufferedReader(isr)
        val stringBuffer = StringBuffer()

        for (msg in br.lines()) {
            stringBuffer.append(msg)
        }

        br.close()
        return stringBuffer.toString()
    }

    @Throws(IOException::class)
    fun controllerFileReplace(javaTemplate: String, layoutTemplate: String) {
        val inputFile = "src/setup/class.csv"
        val isr = InputStreamReader(FileInputStream(inputFile))
        val br = BufferedReader(isr)
        lateinit var repText: Array<String>

        for (csv in br.lines()) {
            val csvList = csv.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (csvList[0] == "Number") {
                repText = csvList.clone()
            } else {
                val name = csvList[2];
                val packageName = csvList[3]
                val activityPackageName = csvList[3] + "." + csvList[4]
                val title = csvList[5]
                val xmlName = "activity_" + csvList[2].toSnakeCase()

                var temp = javaTemplate.replace("{name}", name)
                temp = temp.replace("{className}", name + "Activity")
                temp = temp.replace("{activityPackageName}", activityPackageName)
                temp = temp.replace("{package}", packageName)
                temp = temp.replace("{xmlName}", xmlName)
                temp = temp.replace("{title}", title + "\n")
                temp = temp.replace("/**", "/**\n")
                temp = temp.replace(" */", " */\n")
                temp = temp.replace(";", ";\n")
                temp = temp.replace("{", "{\n")
                temp = temp.replace("}", "}\n")

                val outJavaPath = "out/src/java/"
                val newDir = File(outJavaPath)
                newDir.mkdir()

                val file1Str = File(outJavaPath + name + "Activity.java").absolutePath
                val file = File(file1Str)
                if (file.exists()) {
                    file.delete()
                }

                val javaWp = PrintWriter(BufferedWriter(FileWriter(file)))
                javaWp.print(temp)
                javaWp.close()

                // Layout Output
                val outLayoutPath = "out/src/layout/"
                val newLayoutDir = File(outLayoutPath)
                newLayoutDir.mkdir()

                val fileLayoutStr = File(outLayoutPath + xmlName + ".xml").absolutePath
                val layoutFile = File(fileLayoutStr)
                if (layoutFile.exists()) {
                    layoutFile.delete()
                }

                val layoutWp = PrintWriter(BufferedWriter(FileWriter(layoutFile)))
                layoutWp.print(layoutTemplate)
                layoutWp.close()

                // OutPutComment
                println("        <activity android:name=\"" + "." + csvList[4] + "." + name + "Activity\"\n" +
                        "            android:exported=\"false\"\n" +
                        "            android:screenOrientation=\"portrait\"/>")
            }
        }

        br.close()
    }

    private fun String.toSnakeCase(): String {
        var text: String = ""
        var isFirst = true
        this.forEach {
            if (it.isUpperCase()) {
                if (isFirst) isFirst = false
                else text += "_"
                text += it.toLowerCase()
            } else {
                text += it
            }
        }
        return text
    }
}
