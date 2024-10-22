package com.example.newsly.data.api

import com.example.newsly.data.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun fetchNews(
        @Query("apiKey") apiKey: String,
        @Query("category") category: String
    ) : Response<NewsResponse>
}