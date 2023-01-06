package com.fortyone.weathertest.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.size.Scale
import com.fortyone.weathertest.R
import com.fortyone.weathertest.databinding.FragmentDisplayBinding

class DisplayFragment : Fragment() {

    private var _binding: FragmentDisplayBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDisplayBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        //Update the views when the ViewModel data is updated, occurs upon selecting a new location
        viewModel.weather.observe(viewLifecycleOwner) { newWeather ->
            binding.txtLocation.text = viewModel.location?.name + " , " + viewModel.location?.state
            binding.txtDescription.text = newWeather.current.weather[0].main
            binding.txtTemp.text = getString(R.string.temp, newWeather.current.temp.toInt().toString())
            binding.txtRealFeel.text = getString(R.string.real_feel, newWeather.current.feels_like.toInt().toString())
            binding.rvForecast.adapter?.notifyDataSetChanged()
        }

        //Fetch weather image representation using Coil Library
        viewModel.imgUrl.observe(viewLifecycleOwner) { newImageURL ->
            binding.imgWeather.load(newImageURL.toUri().buildUpon().scheme("https").build()) {
                placeholder(R.drawable.loading_placeholder)
                error(R.drawable.broken_placeholder)
                size(128,128)
                scale(Scale.FIT)
            }
        }

        val adapter = ForecastAdapter(viewModel)
        binding.rvForecast.adapter = adapter
        binding.rvForecast.layoutManager = LinearLayoutManager(context)
        return binding.root
    }



}