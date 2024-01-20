package com.sumeyra.kotlinweather.dependency_injection

import com.sumeyra.kotlinweather.repository.WeatherDataRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { WeatherDataRepository(weatherAPI = get()) }
}