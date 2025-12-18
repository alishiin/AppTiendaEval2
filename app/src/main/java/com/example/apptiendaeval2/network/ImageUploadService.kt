package com.example.apptiendaeval2.network

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Servicio para manejar la subida de imágenes a la API
 */
object ImageUploadService {

    /**
     * Crea MultipartBody.Part desde URI para subir a la API
     */
    fun createImagePart(context: Context, imageUri: Uri, partName: String = "imagen"): MultipartBody.Part? {
        return try {
            val file = getFileFromUri(context, imageUri) ?: return null
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData(partName, file.name, requestFile)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Crea MultipartBody.Part desde Bitmap para subir a la API
     */
    fun createImagePartFromBitmap(context: Context, bitmap: Bitmap, partName: String = "imagen"): MultipartBody.Part? {
        return try {
            val file = createTempFileFromBitmap(context, bitmap)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData(partName, file.name, requestFile)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Sube una imagen desde URI a la API
     * @param context Contexto de la aplicación
     * @param imageUri URI de la imagen seleccionada
     * @return URL de la imagen subida o null si falla
     */
    suspend fun uploadImage(context: Context, imageUri: Uri): String? {
        return try {
            val file = getFileFromUri(context, imageUri)
            file?.let {
                uploadImageFile(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Sube una imagen desde Bitmap (tomada con cámara) a la API
     * @param context Contexto de la aplicación
     * @param bitmap Bitmap de la imagen
     * @return URL de la imagen subida o null si falla
     */
    suspend fun uploadBitmap(context: Context, bitmap: Bitmap): String? {
        return try {
            val file = createTempFileFromBitmap(context, bitmap)
            uploadImageFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Sube el archivo de imagen a la API
     * @param file Archivo de imagen
     * @return URL de la imagen subida o null si falla
     */
    private suspend fun uploadImageFile(file: File): String? {
        return try {
            // TODO: Implementar llamada real a la API para subir imagen
            // Por ahora, retornamos el nombre del archivo como URL
            // En producción, deberías tener un endpoint en tu API para subir imágenes

            // Ejemplo de implementación real:
            // val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            // val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
            // val response = ApiService.create().uploadImage(body)
            // if (response.isSuccessful) {
            //     response.body()?.imageUrl
            // } else {
            //     null
            // }

            // Por ahora retornamos el nombre del archivo
            file.name
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Convierte un URI en un File
     */
    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
            tempFile.outputStream().use { outputStream ->
                inputStream?.copyTo(outputStream)
            }
            inputStream?.close()
            tempFile
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Crea un archivo temporal desde un Bitmap
     */
    private fun createTempFileFromBitmap(context: Context, bitmap: Bitmap): File {
        val file = File.createTempFile("camera_", ".jpg", context.cacheDir)
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }
        return file
    }

    /**
     * Obtiene el nombre del archivo desde un URI
     */
    fun getFileNameFromUri(context: Context, uri: Uri): String {
        return try {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                val nameIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                if (it.moveToFirst() && nameIndex >= 0) {
                    it.getString(nameIndex)
                } else {
                    "image_${System.currentTimeMillis()}.jpg"
                }
            } ?: "image_${System.currentTimeMillis()}.jpg"
        } catch (e: Exception) {
            "image_${System.currentTimeMillis()}.jpg"
        }
    }
}

