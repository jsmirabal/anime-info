package com.jsmirabal.animeinfo.presentation.di

import com.google.gson.Gson
import com.jsmirabal.animeinfo.data.repository.AnimeRepositoryImpl
import com.jsmirabal.animeinfo.domain.mapper.AnimeMapper
import com.jsmirabal.animeinfo.domain.repository.AnimeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    companion object {

        @Provides
        @Singleton
        fun provideGson() = Gson()
    }

    @Binds
    abstract fun bindAnimeRepository(impl: AnimeRepositoryImpl): AnimeRepository
}
