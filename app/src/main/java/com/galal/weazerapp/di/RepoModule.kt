package com.galal.weazerapp.di

import com.galal.data.remote.ApiService
import com.galal.data.repo.WeatherRepoImpl
import com.galal.domain.repo.WeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRepo(apiService: ApiService):WeatherRepo{
        return WeatherRepoImpl(apiService)
    }
}