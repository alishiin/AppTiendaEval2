package com.example.apptiendaeval2.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream

object ImageUtils {

    /**
     * Convierte una imagen URI a Base64 para enviar al servidor
     */
    fun uriToBase64(context: Context, uri: Uri, maxSizeKb: Int = 500): String? {
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            // Redimensionar si es muy grande
            val resizedBitmap = resizeBitmap(bitmap, maxSizeKb)

            val outputStream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            val byteArray = outputStream.toByteArray()

            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * Redimensiona un bitmap para que no exceda el tamaño máximo
     */
    private fun resizeBitmap(bitmap: Bitmap, maxSizeKb: Int): Bitmap {
        val maxSize = maxSizeKb * 1024
        val originalSize = bitmap.byteCount

        if (originalSize <= maxSize) {
            return bitmap
        }

        val ratio = Math.sqrt(maxSize.toDouble() / originalSize)
        val newWidth = (bitmap.width * ratio).toInt()
        val newHeight = (bitmap.height * ratio).toInt()

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    /**
     * Obtiene el nombre del archivo desde una URI
     */
    fun getFileName(context: Context, uri: Uri): String? {
        var fileName: String? = null
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = cursor.getString(nameIndex)
                }
            }
        }
        return fileName ?: "imagen_${System.currentTimeMillis()}.jpg"
    }
}

