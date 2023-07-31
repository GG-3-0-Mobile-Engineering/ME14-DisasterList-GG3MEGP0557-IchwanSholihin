package com.ichwan.disasterlist.presentation.ui.disaster

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ichwan.disasterlist.R
import com.ichwan.disasterlist.data.FilterableItem
import com.ichwan.disasterlist.databinding.ItemFilterableBinding

class FilterAdapter(var filter: ArrayList<FilterableItem>) :
    RecyclerView.Adapter<FilterAdapter.FilterHolder>() {

    private var selectedPos = -1

    inner class FilterHolder(private val binding: ItemFilterableBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.cardFilterable.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION){
                    val prevSelected = selectedPos
                    selectedPos = adapterPosition
                    notifyItemChanged(prevSelected)
                    notifyItemChanged(selectedPos)
                }
            }
        }

        fun bind(name: String){
            binding.disasterName.text = name
            updateBackgroundColor()
        }

        private fun updateBackgroundColor(){
            if (adapterPosition == selectedPos){
                binding.cardFilterable.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.teal_200))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterHolder {
        val binding = ItemFilterableBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FilterHolder(binding)
    }

    override fun getItemCount(): Int = filter.size

    override fun onBindViewHolder(holder: FilterHolder, position: Int) {
        val currentItem = filter[position]
        holder.bind(currentItem.name)
    }

}