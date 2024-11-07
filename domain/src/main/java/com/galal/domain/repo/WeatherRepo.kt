package com.galal.domain.repo

import com.galal.domain.models.DailyForecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    suspend fun getDailyWeatherFromRemote(lat:Double, long:Double):Flow<DailyForecast>

}