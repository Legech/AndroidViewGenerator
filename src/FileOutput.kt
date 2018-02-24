import java.io.*


class FileOutput {

    private fun initBufferedReader(str: String) = BufferedReader(
            InputStreamReader(FileInputStream(File(str).absolutePath))
    )

    @Throws(IOException::class)
    fun getJavaFileStr(): String {
        val stringBuffer = StringBuffer()

        val buffer = initBufferedReader("src/template/Template.java.txt")
        buffer.lines().forEach {
            stringBuffer.append(it)
        }

        buffer.close()
        return stringBuffer.toString()
    }

    @Throws(IOException::class)
    fun getXmlFileStr(): String {
        val stringBuffer = StringBuffer()

        val buffer = initBufferedReader("src/template/Template.xml.txt")
        buffer.lines().forEach {
            stringBuffer.append(it)
        }

        buffer.close()
        return stringBuffer.toString()
    }

    @Throws(IOException::class)
    fun controllerFileReplace(javaTemplate: String, layoutTemplate: String) {
        val br = BufferedReader(InputStreamReader(FileInputStream("src/setup/class.csv")))

        br.lines().forEach {
            val csvList = it.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (csvList[0] != "Number") {
                val name = csvList[2]
                val packageName = csvList[3]
                val activityPackageName = csvList[3] + "." + csvList[4]
                val title = csvList[5]
                val xmlName = "activity_" + csvList[2].toSnakeCase()

                val temp = javaTemplate
                        .replace("{name}", name)
                        .replace("{className}", name + "Activity")
                        .replace("{activityPackageName}", activityPackageName)
                        .replace("{package}", packageName)
                        .replace("{xmlName}", xmlName)
                        .replace("{title}", title + "\n")
                        .replace("/**", "/**\n")
                        .replace(" */", " */\n")
                        .replace(";", ";\n")
                        .replace("{", "{\n")
                        .replace("}", "}\n")

                val outJavaPath = "out/src/java/"
                val newDir = File(outJavaPath)
                newDir.mkdir()

                val activityFileStr = File("$outJavaPath${name}Activity.java").absolutePath
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
                layoutWp.print(layoutTemplate)
                layoutWp.close()

                // OutPutComment
                println("<activity android:name=\".${csvList[4]}.${name}Activity\"\n" +
                        "android:exported=\"false\"\n" +
                        " android:screenOrientation=\"portrait\"/>")
            }
        }

        br.close()
    }

    private fun String.toSnakeCase(): String {
        val stringBuffer = StringBuffer()
        var isFirst = true
        this.forEach {
            if (it.isUpperCase()) {
                if (isFirst) isFirst = false
                else stringBuffer.append("_")
                stringBuffer.append(it.toLowerCase())
            } else {
                stringBuffer.append(it)
            }
        }
        return stringBuffer.toString()
    }
}
