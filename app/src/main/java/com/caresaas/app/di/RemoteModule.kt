package com.caresaas.app.di

import com.caresaas.app.BuildConfig
import com.caresaas.app.data.api.CareSaasService
import com.caresaas.app.data.api.CareSaasServiceFactory
import com.caresaas.app.data.api.TokenInterceptor
import com.caresaas.app.data.source.CareSaasRemoteDataSource
import com.caresaas.app.data.source.CareSaasRemoteDataSourceImpl
import com.caresaas.app.domain.repository.CareSaasRepository
import com.caresaas.app.domain.repository.CareSaasRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteModule {

    @get:Binds
    val CareSaasRemoteDataSourceImpl.careSaasRemoteImpl: CareSaasRemoteDataSource

    @get:Binds
    val CareSaasRepositoryImpl.careSaasRespositoryImpl: CareSaasRepository


    companion object {
        @[Provides Singleton]
        fun provideApiService(tokenInterceptor: TokenInterceptor): CareSaasService =
            CareSaasServiceFactory.createApiService(BuildConfig.DEBUG, tokenInterceptor)
    }

}