package com.galal.data.repo

import com.galal.data.remote.ApiService
import com.galal.data.utils.Utils.Companion.API_KEY
import com.galal.domain.models.DailyForecast
import com.galal.domain.repo.WeatherRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class WeatherRepoImpl(private val apiService: ApiService):WeatherRepo {
    override suspend fun getDailyWeatherFromRemote(lat: Double, long: Double): Flow<DailyForecast> {
        return flowOf(apiService.getDailyWeather(lat, long, API_KEY).body()!!)
    }
}