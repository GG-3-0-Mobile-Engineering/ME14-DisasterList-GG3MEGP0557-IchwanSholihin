package com.ichwan.disasterlist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ichwan.disasterlist.databinding.ActivityMainBinding
import com.ichwan.disasterlist.disasters.DisasterFragment
import com.ichwan.disasterlist.filterables.FilterAdapter
import com.ichwan.disasterlist.filterables.FilterableItem
import com.ichwan.disasterlist.settings.SettingActivity
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val disasterFragment = DisasterFragment()
    private var filterList = ArrayList<FilterableItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataFilter(this)

        binding.handleSheet.setOnClickListener {
            handleBottomSheet()
        }

        if (savedInstanceState == null){
            showFragment()
        }

        binding.btnSet.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        Configuration.getInstance().load(applicationContext, getPreferences(MODE_PRIVATE))
        binding.apply {
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.setBuiltInZoomControls(true)
            mapView.setMultiTouchControls(true)

            val initPosition = GeoPoint(106.834552223,-6.1890435618)
            val mapController: IMapController = mapView.controller
            mapController.setZoom(15.0)
            mapController.setCenter(initPosition)
        }

    }

    private fun handleBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.coordinatLayout)
        bottomSheetBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        bottomSheetBehavior.isFitToContents = false
        bottomSheetBehavior.halfExpandedRatio = 0.75f
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun showFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.bottom_sheet, disasterFragment)
            .commit()
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

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }
}