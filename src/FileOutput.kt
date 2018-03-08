import java.io.*


class FileOutput {

    private lateinit var javaActivityStr: String
    private lateinit var javaFragmentStr: String
    private lateinit var layoutStr: String

    private fun initBufferedReader(str: String) = BufferedReader(
            InputStreamReader(FileInputStream(File(str).absolutePath))
    )

    @Throws(IOException::class)
    fun getJavaActivityFileStr() {
        val stringBuffer = StringBuffer()

        val buffer = initBufferedReader("src/template/TemplateActivity.java.txt")
        buffer.lines().forEach {
            stringBuffer.append(it)
        }

        buffer.close()
        javaActivityStr = stringBuffer.toString()
    }

    @Throws(IOException::class)
    fun getJavaFragmentFileStr() {
        val stringBuffer = StringBuffer()

        val buffer = initBufferedReader("src/template/TemplateFragment.java.txt")
        buffer.lines().forEach {
            stringBuffer.append(it)
        }

        buffer.close()
        javaFragmentStr = stringBuffer.toString()
    }

    @Throws(IOException::class)
    fun setLayoutFileStr() {
        val stringBuffer = StringBuffer()

        val buffer = initBufferedReader("src/template/Template.xml.txt")
        buffer.lines().forEach {
            stringBuffer.append(it)
        }

        buffer.close()
        layoutStr = stringBuffer.toString()
    }

    @Throws(IOException::class)
    fun controllerFileReplace() {
        val br = BufferedReader(InputStreamReader(FileInputStream("src/setup/class.csv")))

        br.lines().forEach {
            val csvList = it.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (csvList[0] != "Number") {
                val name = csvList[2]
                val appPackage = csvList[3]
                val filePackageName = csvList[3] + "." + csvList[4]
                val viewType = csvList[5] // Activity or Fragment
                val title = csvList[6]
                val xmlName = if (viewType == "Activity") {
                    "activity_"
                } else {
                    "fragment_"
                } + csvList[2].toSnakeCase()

                val temp = when (viewType) {
                    "Activity" -> javaActivityStr
                            .replace("{name}", name)
                            .replace("{className}", name + viewType)
                            .replace("{packageName}", filePackageName)
                            .replace("{appPackage}", appPackage)
                            .replace("{xmlName}", xmlName)
                            .replace("{title}", "$title $viewType.\n")
                            .replace("/**", "/**\n")
                            .replace(" */", " */\n")
                            .replace(";", ";\n")
                            .replace("{", "{\n")
                            .replace("}", "}\n")
                    "Fragment" -> javaFragmentStr
                            .replace("{name}", name)
                            .replace("{className}", name + viewType)
                            .replace("{packageName}", filePackageName)
                            .replace("{appPackage}", appPackage)
                            .replace("{xmlName}", xmlName)
                            .replace("{title}", "$title $viewType.\n")
                            .replace("/**", "/**\n")
                            .replace(" */", " */\n")
                            .replace(";", ";\n")
                            .replace("{", "{\n")
                            .replace("}", "}\n")
                    else -> throw IOException("viewType Error.")
                }
                val outJavaPath = "out/src/java/"
                val newDir = File(outJavaPath)
                newDir.mkdir()

                val activityFileStr = File("$outJavaPath${name}$viewType.java").absolutePath
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
