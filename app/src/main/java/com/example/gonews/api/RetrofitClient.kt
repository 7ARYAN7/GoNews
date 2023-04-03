package com.example.newsaqq.api

import com.example.newsaqq.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
  val newsApi : NewsApi by lazy {
      retrofit.create(NewsApi::class.java)
  }
}