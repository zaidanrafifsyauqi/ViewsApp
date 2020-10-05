package com.example.viewsapp.remote

import com.example.viewsapp.remote.POJO.ResponseNews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterfaces {

    //Get URL path : v2/top-headlines
    //http://newsapi.org/v2/top-headlines
    @GET("/v2/top-headlines")
    suspend fun topHeadlines(
        //Menambah Query country
        //sehingga url menjadi
        //http://newsapi.org/v2/top-headlines?country=id
        @Query("country") country : String
    ) : Response<ResponseNews>
}