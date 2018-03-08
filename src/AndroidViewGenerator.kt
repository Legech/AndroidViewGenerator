
import java.io.IOException

fun main(args: Array<String>) {

    println("_/_/_/_/_/_/_/_/_/_/_/_/_/")
    println("Start Generator")
    println("_/_/_/_/_/_/_/_/_/_/_/_/_/")

    val fileOutput = FileOutput()

    try {
        fileOutput.getJavaActivityFileStr()
    } catch (var6: IOException) {
        println("getJavaActivityFileStr() Error.")
        println("output failed.")
        println(var6.toString())
        return
    }
    try {
        fileOutput.getJavaFragmentFileStr()
    } catch (var6: IOException) {
        println("getJavaActivityFileStr() Error.")
        println("output failed.")
        println(var6.toString())
        return
    }

    try {
        fileOutput.setLayoutFileStr()
    } catch (var6: IOException) {
        println("setLayoutFileStr() Error.")
        println("output failed.")
        println(var6.toString())
        return
    }

    try {
        fileOutput.controllerFileReplace()
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
