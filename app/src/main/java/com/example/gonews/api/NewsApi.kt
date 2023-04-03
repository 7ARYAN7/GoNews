package com.example.newsaqq.api

import com.example.newsaqq.model.News
import com.example.newsaqq.utils.Constants.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country") country: String, @Query("page") page:Int) : Call<News>

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlinesByCategory(@Query("country") country: String, @Query("category") category: String) : Call<News>
}
//https://newsapi.org/v2/top-headlines?apiKey=d70f31bef1684b008ae058f185f25602&country=us&category=business
//https://newsapi.org/v2/top-headlines?apiKey=d70f31bef1684b008ae058f185f25602&country=us&category=business
//country=us&category=sports