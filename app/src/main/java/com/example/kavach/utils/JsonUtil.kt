package com.example.kavach.utils

import android.content.Context
import android.content.res.AssetManager
import java.io.IOException

class JsonUtil {

    companion object {
        @Throws(IOException::class)
        fun loadJSONFromAsset(assets: AssetManager, fileName: String): String? {

            val inputStream = assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            return String(buffer, Charsets.UTF_8)
        }
    }
}
