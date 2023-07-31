package com.ichwan.disasterlist.presentation.ui.disaster

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ichwan.disasterlist.R
import com.ichwan.disasterlist.data.Geometries
import com.ichwan.disasterlist.databinding.ItemListBinding

class DisasterAdapter(var context: Context, var list: ArrayList<Geometries>) : RecyclerView.Adapter<DisasterAdapter.DisasterHolder>() {

    inner class DisasterHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, data: Geometries){
            binding.apply {
                txDisaster.text = data.properties.disaster_type
                txPlace.text = data.properties.tags.instance_region_code
                Glide.with(context).load(data.properties.image_url).placeholder(R.drawable.ic_place).into(imgPlace)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisasterHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DisasterHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DisasterHolder, position: Int) {
        holder.bind(
            context,
            list[position]
        )
    }

    fun setData(data: ArrayList<Geometries>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}