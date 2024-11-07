package com.galal.domain.models


data class WeatherEntity(
    val description: String,
    val currentTemp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val clouds: Int,
    val sunrise: Long,
    val sunset: Long,
    val date: String,
    val latitude: Double,
    val longitude: Double,
    val lottie: Int
)