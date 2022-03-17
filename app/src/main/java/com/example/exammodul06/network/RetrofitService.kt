package com.example.exammodul06.network

import com.example.exammodul06.model.CardModel
import com.example.exammodul06.model.CardResp
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    companion object {
        const val headers: String = "Content-type:application/json"
    }

    @Headers(headers)
    @GET("cards")
    fun listPhotos(): Call<ArrayList<CardResp>>

    @Headers(headers)
    @POST("cards")
    fun createEmployee(@Body cardResp: CardResp): Call<CardResp>

}