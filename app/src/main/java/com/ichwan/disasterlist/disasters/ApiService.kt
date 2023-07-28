package com.ichwan.disasterlist.disasters

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("reports")
    fun getReports(@Query("timeperiod") timeperiod: Long): Call<ResponseData>
}