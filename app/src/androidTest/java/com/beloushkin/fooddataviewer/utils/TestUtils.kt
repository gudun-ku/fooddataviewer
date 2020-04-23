package com.beloushkin.fooddataviewer.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream

fun readJson(context: Context, assetLocation: String): String {
    var json: String = ""
    var inputStream: InputStream? = null
    try {
        inputStream = context.assets.open(assetLocation)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        json = String(buffer, charset("UTF-8"))
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
    }
    return json
}