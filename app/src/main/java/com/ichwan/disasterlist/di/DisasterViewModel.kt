package com.ichwan.disasterlist.di

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ichwan.disasterlist.data.Geometries
import com.ichwan.disasterlist.data.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DisasterViewModel: ViewModel() {

    private val dataList: ArrayList<Geometries> = ArrayList()

    fun callApiService() {
        val apiService = ApiClient.apiService
        val timePeriod = 86400L
        val call = apiService.getReports(timePeriod)

        call.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    val responseData = response.body()

                    responseData?.result?.objects?.output?.geometries?.let { geometries ->
                        dataList.clear()
                        dataList.addAll(geometries)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.d("Error show disaster ",t.message.toString())
            }
        })
    }

    fun getDataList(): ArrayList<Geometries> {
        return dataList
    }
}