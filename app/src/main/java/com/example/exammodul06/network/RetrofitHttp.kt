package com.example.exammodul06.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHttp {

    val IS_TESTER = true

    private val SERVER_DEVELOPMENT = "https://623303986de3467dbac5e23a.mockapi.io/"
    private val SERVER_PRODUCTION = "https://623303986de3467dbac5e23a.mockapi.io/"

    private val retrofit =
        Retrofit.Builder().baseUrl(server()).addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun server(): String {
        if (IS_TESTER) {
            return SERVER_DEVELOPMENT
        }
        return SERVER_PRODUCTION
    }

    val retrofitService: RetrofitService = retrofit.create(RetrofitService::class.java)

}