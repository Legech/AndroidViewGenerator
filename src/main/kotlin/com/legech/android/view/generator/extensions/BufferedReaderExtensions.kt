package com.legech.android.view.generator.extensions

import java.io.BufferedReader

fun BufferedReader.readAll(): String {
    val stringBuffer = StringBuffer()

    lines().iterator().asSequence().forEachIndexed { index, value ->
        if (index > 0) {
            stringBuffer.append("\n")
        }
        stringBuffer.append(value)
    }

    return stringBuffer.toString()
}