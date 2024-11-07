package com.galal.weazerapp

import com.galal.domain.models.DailyForecast
import com.galal.domain.models.DailyForecastElement

sealed class UiState {
    class Success(val data:DailyForecast ):UiState()
    class Failure(val error:String):UiState()
    object Loading:UiState()
}