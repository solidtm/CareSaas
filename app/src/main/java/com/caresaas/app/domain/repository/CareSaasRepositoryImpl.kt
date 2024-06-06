package com.caresaas.app.domain.repository

import com.caresaas.app.data.mapper.map
import com.caresaas.app.data.model.DomainModel
import com.caresaas.app.data.source.CareSaasRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CareSaasRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val careSaasRemoteDataSource: CareSaasRemoteDataSource
    ): CareSaasRepository{

    override suspend fun login(loginRequest: DomainModel.LoginRequest): DomainModel.LoginResponse = withContext(dispatcher) {
        careSaasRemoteDataSource.login(loginRequest).map()
    }

    override suspend fun getTasks(shortCode: String, careHomeId: String, assignee: String): DomainModel.GetTasksResponse = withContext(dispatcher) {
        careSaasRemoteDataSource.getTasks(shortCode, careHomeId, assignee).map()
    }
}