
import java.io.IOException


fun main(args: Array<String>) {

    println("_/_/_/_/_/_/_/_/_/_/_/_/_/")
    println("Start Generator")
    println("_/_/_/_/_/_/_/_/_/_/_/_/_/")

    val fileOutput = FileOutput()

    val fileJavaString: String
    try {
        fileJavaString = fileOutput.getJavaFileStr()
    } catch (var6: IOException) {
        println("getJavaFileStr() Error.")
        println("output failed.")
        println(var6.toString())
        return
    }

    val fileLayoutString: String
    try {
        fileLayoutString = fileOutput.getXmlFileStr()
    } catch (var6: IOException) {
        println("getXmlFileStr() Error.")
        println("output failed.")
        println(var6.toString())
        return
    }

    try {
        fileOutput.controllerFileReplace(fileJavaString, fileLayoutString)
    } catch (var5: IOException) {
        println("controllerFileReplace() Error.")
        println("output failed.")
        println(var5.message)
        return
    }

    println("_/_/_/_/_/_/_/_/_/_/_/_/_/")
    println("End Generate Success")
    println("_/_/_/_/_/_/_/_/_/_/_/_/_/")

}
