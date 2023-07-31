package com.ichwan.disasterlist.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("reports")
    fun getReports(
        @Query("timeperiod") timeperiod: Long
    ): Call<ResponseData>
}