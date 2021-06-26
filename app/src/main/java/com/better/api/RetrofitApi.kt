package com.better.api

import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("storage")
    suspend fun updateModelPrediction(@Query("id") id: Long): String
}
