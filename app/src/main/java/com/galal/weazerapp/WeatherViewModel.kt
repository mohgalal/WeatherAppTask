package com.galal.weazerapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galal.domain.models.City
import com.galal.domain.models.Clouds
import com.galal.domain.models.Coord
import com.galal.domain.models.DailyForecast
import com.galal.domain.models.DailyForecastElement
import com.galal.domain.models.Main
import com.galal.domain.models.Sys
import com.galal.domain.models.Wind
import com.galal.domain.usecase.GetWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeather
) : ViewModel() {

    private var _weatherDays: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
        val weatherDays = _weatherDays.asStateFlow()

    fun getWeatherFromRemote(lat: Double, long: Double) {
        viewModelScope.launch {
            try {
                getWeatherUseCase.getDailyWeather(lat, long)
                    .catch { e -> _weatherDays.emit(UiState.Failure(e.message.toString())) }
                    .collect {
                    //var dailyData = processForecastData(it.list)
                    _weatherDays.emit(UiState.Success(it))
                }

            } catch (e: Exception) {
                _weatherDays.emit(UiState.Failure(e.message.toString()))
            }
        }
    }

}

