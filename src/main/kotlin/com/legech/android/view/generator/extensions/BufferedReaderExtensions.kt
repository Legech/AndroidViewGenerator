package com.legech.android.view.generator.extensions

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.legech.android.view.generator.entity.OutputEntity
import java.io.BufferedReader
import java.io.InputStreamReader

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

fun Any.bufferedString(string: String) =
        BufferedReader(InputStreamReader(javaClass.getResourceAsStream(string), "UTF-8")).let {
            val str = it.readAll()
            it.close()
            str
        }

fun Any.createOutputEntity(string: String) =
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
                .fromJson<OutputEntity>(BufferedReader(InputStreamReader(javaClass.getResourceAsStream(string), "UTF-8")).readAll(), OutputEntity::class.java)