package com.sumeyra.kotlinweather.model

import com.google.gson.annotations.SerializedName

data class WeatherLocation(
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double
)



