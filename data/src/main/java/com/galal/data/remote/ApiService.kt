package com.galal.data.remote

import com.galal.domain.models.DailyForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.openweathermap.org/data/2.5/forecast?lat=33.44&lon=-97.23&appid=5fbad51ee66fc654f1b3b46c8da60af5
interface ApiService {
    @GET("forecast")
    suspend fun getDailyWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): Response<DailyForecast>
}