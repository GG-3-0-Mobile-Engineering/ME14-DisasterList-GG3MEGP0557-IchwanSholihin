package com.ichwan.disasterlist.presentation.ui.disaster

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ichwan.disasterlist.R
import com.ichwan.disasterlist.data.FilterableItem
import com.ichwan.disasterlist.databinding.FragmentMapsBinding
import org.osmdroid.api.IMapController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint

class MapsFragment : Fragment() {

    private lateinit var binding: FragmentMapsBinding
    private var filterList = ArrayList<FilterableItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)

        getDataFilter(requireContext())

        binding.btnSet.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.toSetting)
        }

        binding.btnList.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.toListDisaster)
        }

        binding.apply {
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.setBuiltInZoomControls(true)
            mapView.setMultiTouchControls(true)

            val initPosition = GeoPoint(106.834552223,-6.1890435618)
            val mapController: IMapController = mapView.controller
            mapController.setZoom(15.0)
            mapController.setCenter(initPosition)
        }

        return binding.root
    }

    private fun getDataFilter(context: Context) {
        filterList.add(FilterableItem("Banjir","flood"))
        filterList.add(FilterableItem("Kebakaran", "fire"))
        filterList.add(FilterableItem("Gunung Meletus","volcano"))
        filterList.add(FilterableItem("Gempa Bumi", "earthquake"))
        filterList.add(FilterableItem("Kabut Asap", "haze"))

        val adapter = FilterAdapter(filterList)
        binding.apply {
            rvFilterable.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvFilterable.adapter = adapter
        }
    }
}