package com.example.haber.restApi

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApiClient {
    companion object {
        fun getClient(): Retrofit {
            return Retrofit.Builder().baseUrl(BaseURL.kaynak_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
    }
}
