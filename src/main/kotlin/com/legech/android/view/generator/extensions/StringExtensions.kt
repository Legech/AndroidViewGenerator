package com.legech.android.view.generator.extensions

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
