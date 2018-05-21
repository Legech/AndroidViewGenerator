package com.legech.android.view.generator

import com.legech.android.view.generator.model.OutputModel
import java.io.IOException

fun main(args: Array<String>) {

    println("_/_/_/_/_/_/_/_/_/_/_/_/_/")
    println("Start Generator")
    println("_/_/_/_/_/_/_/_/_/_/_/_/_/")

    val fileOutput = OutputModel()
    fileOutput.initData()

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
