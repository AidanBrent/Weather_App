package com.fortyone.weathertest.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.fortyone.weathertest.R
import com.fortyone.weathertest.databinding.ForecastCardBinding
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    inner class ForecastViewHolder(private val binding: ForecastCardBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(imgUrl: String) {
            binding.apply {
                txtDate.text = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(
               viewModel.weather.value!!.daily[adapterPosition].dt * 1000L
                )
                txtForecastDesc.text = viewModel.weather.value!!.daily[adapterPosition].weather[0].description
                txtForecastMin.text =  txtForecastMin.context.getString(R.string.minTemp, viewModel.weather.value!!.daily[adapterPosition].temp.min.toInt().toString())
                txtForecastMax.text = txtForecastMax.context.getString(R.string.maxTemp, viewModel.weather.value!!.daily[adapterPosition].temp.max.toInt().toString())
                imgForecastWeather.load(imgUrl.toUri().buildUpon().scheme("https").build()) {
                    placeholder(R.drawable.loading_placeholder)
                    error(R.drawable.broken_placeholder)
                    size(128,128)
                    scale(Scale.FIT)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapter.ForecastViewHolder {
        return ForecastViewHolder(
            ForecastCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: ForecastAdapter.ForecastViewHolder, position: Int) {
        val icon = viewModel.weather.value!!.daily[position].weather[0].icon
        val imgUrl = "http://openweathermap.org/img/wn/$icon@2x.png"
        holder.bind(imgUrl)
    }

    override fun getItemCount(): Int {
        return 7
    }
}