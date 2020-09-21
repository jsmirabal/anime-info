package com.jsmirabal.animeinfo.presentation.di

import com.jsmirabal.animeinfo.data.service.AnimeService
import com.jsmirabal.animeinfo.data.service.AnimeServiceImpl
import com.jsmirabal.animeinfo.data.service.api.AnimeApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class ServiceModule {

    companion object {
        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            this.level = (HttpLoggingInterceptor.Level.BODY)
                        }
                    )
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @Provides
        @Singleton
        fun provideAnimeApi(retrofit: Retrofit): AnimeApi = retrofit.create(AnimeApi::class.java)
    }

    @Binds
    abstract fun bindAnimeService(impl: AnimeServiceImpl): AnimeService
}
