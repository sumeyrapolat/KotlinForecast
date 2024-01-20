package com.sumeyra.kotlinweather.util

import android.app.Application
import com.sumeyra.kotlinweather.dependency_injection.networkModule
import com.sumeyra.kotlinweather.dependency_injection.repositoryModule
import com.sumeyra.kotlinweather.dependency_injection.serializerModule
import com.sumeyra.kotlinweather.dependency_injection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppConfig : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppConfig)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    serializerModule,
                    viewModelModule

                ))
        }
    }

}
