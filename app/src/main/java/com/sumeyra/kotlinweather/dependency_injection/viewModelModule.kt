package com.sumeyra.kotlinweather.dependency_injection

import com.sumeyra.kotlinweather.viewmodel.ListViewModel
import com.sumeyra.kotlinweather.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
        viewModel { ListViewModel(weatherDataRepository = get())}
        viewModel { WeatherViewModel(weatherDataRepository = get()) }
    }
