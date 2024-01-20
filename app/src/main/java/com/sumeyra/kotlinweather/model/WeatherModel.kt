package com.sumeyra.kotlinweather.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Verilerle ilgili tüm işlemler

sealed class WeatherData

data class CurrentWeather(
    val icon: String,
    val humidity : Int,
    val changeOfRain: Int,
    val wind: Float,
    val temperature: Float
): WeatherData()

data class Forecast(
    val time: String,
    val temperature: Float,
    val feelsLikeTemperature: Float,
    val icon: String,
    val text: String
): WeatherData()

