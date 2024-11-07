package com.galal.domain.usecase

import com.galal.domain.repo.WeatherRepo

class GetWeather(private val weatherRepo: WeatherRepo) {
    suspend fun getDailyWeather(lat:Double, long:Double) = weatherRepo.getDailyWeatherFromRemote(lat, long)
}