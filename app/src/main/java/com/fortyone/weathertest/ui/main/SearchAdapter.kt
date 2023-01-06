package com.fortyone.weathertest.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fortyone.weathertest.R
import com.fortyone.weathertest.databinding.LocationCardBinding
import com.fortyone.weathertest.ui.main.datamodel.Location

class SearchAdapter(private val onClick: (location: Location) -> Unit) : ListAdapter<Location,SearchAdapter.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.lat == newItem.lat
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LocationCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClick)
    }

    inner class ViewHolder(
        private val binding: LocationCardBinding,
        val onClick: (location: Location) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentLocation: Location? = null

        init {
            binding.txtLocationName.setOnClickListener {
                currentLocation?.let {
                    onClick(it)
                }
            }
        }

        fun bind(location: Location) {
            currentLocation = location

            //Format search result to be displayed upon location search
            var resultString = binding.root.context.getString(R.string.search_location, location.name, location.state, location.country)
            resultString = resultString.replace("null, ", "")

            binding.txtLocationName.text = resultString
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = getItem(position)
        holder.bind(location)
    }
}
