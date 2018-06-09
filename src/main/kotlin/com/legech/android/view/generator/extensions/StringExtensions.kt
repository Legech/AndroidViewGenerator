package com.legech.android.view.generator.extensions

import java.io.BufferedReader
import java.io.InputStreamReader

fun String.toSnakeCase(): String {
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

fun String.bufferedString(str: String) =
        BufferedReader(InputStreamReader(javaClass.getResourceAsStream(str), "UTF-8"))