package com.sumeyra.kotlinweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumeyra.kotlinweather.model.CurrentWeather
import com.sumeyra.kotlinweather.repository.WeatherDataRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherDataRepository: WeatherDataRepository) : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherDataState>()
    val weatherData : LiveData<WeatherDataState> get() = _weatherData


    fun getWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            emitWeatherDataUIState(isLoading = true)
            weatherDataRepository.getWeatherData(latitude,longitude)?.let { weatherData->
                emitWeatherDataUIState(
                    currentWeather = CurrentWeather(
                        icon = weatherData.current.condition.icon,
                        temperature = weatherData.current.temperature,
                        wind = weatherData.current.wind,
                        humidity = weatherData.current.humidity,
                        changeOfRain = weatherData.forecast.forecastDay.first().day.changeOfRain
                    )
                )
            } ?: emitWeatherDataUIState(error = "Unable to fetch weather data !")
        }
    }

    private fun emitWeatherDataUIState(
        isLoading: Boolean= false,
        currentWeather: CurrentWeather? = null,
        error: String? = null
    ){
        val weatherDataState = WeatherDataState(isLoading,currentWeather,error)
        _weatherData.value = weatherDataState
    }


    data class WeatherDataState(
        val isLoading : Boolean,
        val currentWeather : CurrentWeather?,
        val error : String?
    )

}