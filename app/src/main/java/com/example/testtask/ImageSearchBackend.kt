package com.example.testtask

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


class ImageSearchBackend {
    // Ключ API сервера
    private val apiKey = "b14b0b87652c18c0fc7748f3a6be9c6c22527ae0"

    // URL сервера
    private val baseUrl = "https://google.serper.dev/images"

    // Клиент для выполнения HTTP-запросов
    private val client = OkHttpClient().newBuilder()
        .build()

    // Функция для поиска изображений по запросу
    suspend fun searchImages(query: String): MutableList<Image> {

        // Создание HTTP-запроса для отправки на сервер
        val body = "{\"q\":\"${query}\",\"num\":10}".toRequestBody("application/json".toMediaType())

        // Выполнение запроса
        val request: Request = Request.Builder()
            .url(baseUrl)
            .method("POST", body)
            .addHeader("X-API-KEY", apiKey)
            .addHeader("Content-Type", "application/json")
            .build()
        val response = client.newCall(request).execute()

        val responseBody = response.body?.string() ?: ""

        val gson = Gson()
        val jsonObject = gson.fromJson(responseBody, JsonObject::class.java)

        val imageList = mutableListOf<Image>()
        val images = jsonObject.getAsJsonArray("images")
        if (images != null) {
        for (image in images) {
            val imageObj = image.asJsonObject
            imageList.add(
                Image(
                    imageObj.get("position").asString,
                    imageObj.get("thumbnailUrl").asString,
                    imageObj.get("source").asString,
                    imageObj.get("imageUrl").asString

                )
            )
        }}

        return imageList
    }

    // Данные об изображении
    data class Image(
        val position: String,
        val thumbnailUrl: String,
        val sourceUrl: String,
        val imageUrl: String
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(position)
            parcel.writeString(thumbnailUrl)
            parcel.writeString(sourceUrl)
            parcel.writeString(imageUrl)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Image> {
            override fun createFromParcel(parcel: Parcel): Image {
                return Image(parcel)
            }

            override fun newArray(size: Int): Array<Image?> {
                return arrayOfNulls(size)
            }
        }
    }
}