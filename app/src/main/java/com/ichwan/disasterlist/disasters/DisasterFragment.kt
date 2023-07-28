package com.ichwan.disasterlist.disasters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ichwan.disasterlist.R
import com.ichwan.disasterlist.databinding.FragmentDisasterBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DisasterFragment : Fragment() {

    private lateinit var binding: FragmentDisasterBinding
    private lateinit var adapter: DisasterAdapter
    private var dataList: ArrayList<Geometries> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisasterBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_disasterFragment_to_mapsFragment)
        }

        adapter = DisasterAdapter(requireContext(), dataList)

        binding.apply {
            rvDisaster.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvDisaster.adapter = adapter
        }

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