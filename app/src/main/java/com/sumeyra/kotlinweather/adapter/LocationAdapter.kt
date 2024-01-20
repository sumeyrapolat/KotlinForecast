package com.sumeyra.kotlinweather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sumeyra.kotlinweather.databinding.ListRowBinding
import com.sumeyra.kotlinweather.model.WeatherLocation
import com.sumeyra.kotlinweather.view.ListFragmentDirections

class LocationAdapter ( var locations : ArrayList<WeatherLocation>) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>(){

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data:List<WeatherLocation>){
        locations.clear()
        locations.addAll(data)
        notifyDataSetChanged()
    }

    inner class LocationViewHolder( private val binding: ListRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(weatherLocation: WeatherLocation){
            with(weatherLocation){
                val location ="${name}, ${country}"
                binding.textRemoteLocation.text = location
                binding.root.setOnClickListener{
                    val action = ListFragmentDirections.actionListFragmentToWeatherFragment()
                    Navigation.findNavController(it).navigate(action)
                }
                //binding.root.setOnClickListener{ onLocationClicked(weatherLocation)}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(ListRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(weatherLocation = locations[position])
    }
}