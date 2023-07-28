package com.ichwan.disasterlist.disasters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ichwan.disasterlist.databinding.FragmentModalBottomSheetBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DisasterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentModalBottomSheetBinding
    private lateinit var adapter: DisasterAdapter
    private var dataList: ArrayList<Geometries> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentModalBottomSheetBinding.inflate(inflater, container, false)

        adapter = DisasterAdapter(requireContext(), dataList)
        binding.rvDisaster.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvDisaster.adapter = adapter

        runBlocking {
            binding.pbDisaster.visibility = View.VISIBLE
            callApiService()
        }
        return binding.root
    }

    private suspend fun callApiService() {
        delay(5000)
        binding.pbDisaster.visibility = View.GONE

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
                        adapter.setData(dataList)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {

            }
        })
    }
}