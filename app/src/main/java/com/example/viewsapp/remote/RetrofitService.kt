package com.example.viewsapp.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    //fungsi HttpLoggingInterceptor adalah mengecek status response dari server
    //keterangan status server:
    //200 = response sukses
    //404 = url not found
    //401 = tidak ada otorisasi / API Key belum dimasukkan
    //500 = Masalah di server
    private fun interceptor() : Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

    //client bertugas sebagai alat penghubung ke server
    //di sini tempat kita memasukkan Interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(RequestInterceptor())
        .addInterceptor(interceptor())
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        requestBuilder.addHeader("Authorization", "5c2ab9e27642445db358efcd98a31073")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}