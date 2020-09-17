package com.jsmirabal.animeinfo.presentation.di

import com.jsmirabal.animeinfo.domain.core.DomainCoroutineScope
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class DomainModule {

    @Binds
    @DomainCoroutine
    @Singleton
    abstract fun provideDomainCoroutineScope(impl: DomainCoroutineScope): CoroutineScope
}
