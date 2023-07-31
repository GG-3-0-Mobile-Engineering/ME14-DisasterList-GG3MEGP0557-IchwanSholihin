package com.ichwan.disasterlist.presentation.ui.disaster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ichwan.disasterlist.R
import com.ichwan.disasterlist.databinding.FragmentDisasterBinding
import com.ichwan.disasterlist.data.Geometries
import com.ichwan.disasterlist.di.DisasterViewModel

class DisasterFragment : Fragment() {

    private lateinit var binding: FragmentDisasterBinding
    private lateinit var adapter: DisasterAdapter
    private var dataList: ArrayList<Geometries> = ArrayList()
    private val viewModel: DisasterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisasterBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_disasterFragment_to_mapsFragment)
        }

//        viewModel.viewModelScope.launch {
//            binding.pbDisaster.visibility = View.VISIBLE
//            viewModel.callApiService()
//            binding.pbDisaster.visibility = View.GONE
//
//            setAdapterService()
//        }

        adapter = DisasterAdapter(requireContext(), arrayListOf())

        binding.apply {
            rvDisaster.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvDisaster.adapter = adapter
            rvDisaster.setHasFixedSize(true)
        }

        return binding.root
    }

    private fun setAdapterService() {
        binding.apply {
            adapter = DisasterAdapter(requireContext(), dataList)
            adapter.setData(viewModel.getDataList())

            rvDisaster.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvDisaster.adapter = adapter
        }
    }

}