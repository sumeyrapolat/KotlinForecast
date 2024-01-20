package com.sumeyra.kotlinweather.repository

import com.sumeyra.kotlinweather.model.WeatherInfo
import com.sumeyra.kotlinweather.model.WeatherLocation
import com.sumeyra.kotlinweather.service.WeatherAPI
import retrofit2.http.Query

class WeatherDataRepository(private val weatherAPI: WeatherAPI) {


    //Apide verilen konumu search etmek için ve girilen query i null değilse body döndürmek için
    suspend fun searchLocationFromAPI(query: String): List<WeatherLocation>?{
        val response = weatherAPI.searchLocation(query= query)
        return if (response.isSuccessful) response.body() else null
    }


    //ikinci query için weather infolarını alabilmek için
    suspend fun getWeatherData(latitude:Double, longitude : Double) : WeatherInfo?{
        val response = weatherAPI.getWeatherData(query = "${latitude},${longitude}")
        return if (response.isSuccessful) response.body() else null
    }

}