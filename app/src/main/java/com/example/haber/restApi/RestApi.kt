package com.example.haber.restApi

import com.example.haber.R
import com.example.haber.models.SourcesItem
import com.example.haber.models.SourcesModel
import com.example.haber.models.TopHeadlinesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import javax.xml.transform.Source

interface RestApi {

    @GET("sources")
    fun kaynaklarCagir(@Query("apiKey") apiKey: String): Call<SourcesModel>


    @GET("top-headlines")
    fun haberCagir(@Query("sources") sources: String,  @Query("apiKey") apiKey: String): Call<TopHeadlinesModel>




}
