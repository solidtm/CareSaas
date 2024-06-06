package com.caresaas.app.data.source

import com.caresaas.app.data.api.CareSaasService
import com.caresaas.app.data.model.DomainModel
import com.caresaas.app.data.model.DtoModel
import javax.inject.Inject

class CareSaasRemoteDataSourceImpl @Inject constructor(
    private val careSaasService: CareSaasService,
) : CareSaasRemoteDataSource {


    override suspend fun login(loginRequest: DomainModel.LoginRequest): DtoModel.LoginResponse {
        return careSaasService.login(loginRequest)
    }

    override suspend fun getTasks(shortCode: String, careHomeId: String, assignee: String): DtoModel.GetTasksResponse {
        return careSaasService.getTasks(shortCode, careHomeId, assignee)
    }
}