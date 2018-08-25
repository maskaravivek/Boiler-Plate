package com.maskaravivek.boilerplate.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException

class FileUtils {
    companion object {
        fun getOrCreateFolder(folderName: String): File {
            val file = File(getExternalStorageDir(), folderName)
            if (!file.exists()) {
                file.mkdirs()
            }
            return file
        }

        private fun getExternalStorageDir() = Environment.getExternalStorageDirectory()

        @Throws(IOException::class)
        fun getOrCreateFile(directory: String, fileName: String): File {
            val file = File(getOrCreateFolder(directory), fileName)
            if (!file.exists()) {
                file.createNewFile()
            }
            return file
        }

        @Throws(IOException::class)
        fun getOrCreateFile(fileName: String): File {
            val file = File(getExternalStorageDir(), fileName)
            if (!file.exists()) {
                file.createNewFile()
            }
            return file
        }

        @Throws(IOException::class)
        fun getFile(directory: String, fileName: String): File {
            return File(getOrCreateFolder(directory), fileName)
        }

        @Throws(IOException::class)
        fun getFile(fileName: String): File {
            return File(getExternalStorageDir(), fileName)
        }

        fun deleteFile(file: File): Boolean {
            return file.delete()
        }

        fun loadJsonFromAssetFile(context: Context, fileName: String): String? {
            val json: String
            try {
                val `is` = context.assets.open(fileName)
                val size = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                json = String(buffer, Charsets.UTF_8)
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }

            return json
        }
    }
}