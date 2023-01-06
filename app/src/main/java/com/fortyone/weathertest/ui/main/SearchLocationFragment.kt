package com.fortyone.weathertest.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fortyone.weathertest.databinding.FragmentSearchlocationBinding
import com.fortyone.weathertest.ui.main.datamodel.Location

class SearchLocationFragment : Fragment() {

    private var _binding: FragmentSearchlocationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchlocationBinding.inflate(inflater, container, false)
        val adapter = SearchAdapter {  location -> onLocationClick(location)}
        binding.rvLocations.adapter = adapter
        binding.rvLocations.layoutManager = LinearLayoutManager(this@SearchLocationFragment.context)
        viewModel.locationList.observe(viewLifecycleOwner) { newList ->
            adapter.submitList(newList)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    private fun onLocationClick(location: Location) {
        viewModel.updateLocation(location)
        val action = SearchLocationFragmentDirections.actionSearchLocationFragmentToDisplayFragment()
        findNavController().navigate(action)
    }
}

