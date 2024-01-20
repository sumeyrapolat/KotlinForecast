package com.sumeyra.kotlinweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sumeyra.kotlinweather.databinding.WeatherFragmentBinding
import com.sumeyra.kotlinweather.model.WeatherLocation
import com.sumeyra.kotlinweather.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment() {

    private var _binding : WeatherFragmentBinding? = null
    private val binding get() = _binding!!

    //private var locationUuid=0


    private val weatherViewModel: WeatherViewModel by viewModel()
/*
    companion object{
        const val REQUEST_KEY_LOCATION_SEARCH ="manualLocationSearch"
        const val KEY_LATITUDE = "latitude"
        const val KEY_LONGITUDE = "longitude"
        const val KEY_NAME= "name"
        const val KEY_COUNTRY = "country"
    }
*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WeatherFragmentBinding.inflate(inflater,container,false)
        val view = binding.root
        return view

        /*
        arguments?.let {
            locationUuid = WeatherFragmentArgs.fromBundle(it).locationUuid

        }*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }


    private fun getWeatherData(weatherLocation: WeatherLocation) {
        weatherViewModel.getWeatherData(
                latitude = weatherLocation.lat,
                longitude = weatherLocation.lon
            )
    }





}