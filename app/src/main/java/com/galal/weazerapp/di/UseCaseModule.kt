package com.galal.weazerapp.di

import com.galal.domain.repo.WeatherRepo
import com.galal.domain.usecase.GetWeather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideUseCase(weatherRepo: WeatherRepo):GetWeather{
        return GetWeather(weatherRepo)
    }
}