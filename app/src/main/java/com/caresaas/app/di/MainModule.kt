package com.caresaas.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    fun provideDispatcher() = Dispatchers.IO

    @Provides
    fun provideCoroutineScope() = CoroutineScope(Dispatchers.IO + SupervisorJob())
}