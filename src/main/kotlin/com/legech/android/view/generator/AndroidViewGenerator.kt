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
        fileOutput.fileOutputExecute()
    } catch (var1: Exception) {
        println("controllerFileReplace() Error.")
        println("output failed.")
        println(var1.message)
        return
    }

    println("_/_/_/_/_/_/_/_/_/_/_/_/_/")
    println("End Generate Success")
    println("_/_/_/_/_/_/_/_/_/_/_/_/_/")

}
