package com.sumeyra.kotlinweather.service

import com.sumeyra.kotlinweather.model.WeatherInfo
import com.sumeyra.kotlinweather.model.WeatherLocation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    //58960d8ffa1049b8836172025241201
    companion object{
        const val BASE_URL = "https://api.weatherapi.com/v1/"
        const val API_KEY = "58960d8ffa1049b8836172025241201"
    }

    @GET("search.json")
    suspend fun searchLocation(
        @Query("key") key : String= API_KEY,
        @Query("q") query : String
    ): Response<List<WeatherLocation>>

    @GET("forecast.json")
    suspend fun getWeatherData(
        @Query("key") key :String = API_KEY,
        @Query("q") query : String
    ) : Response<WeatherInfo>
}