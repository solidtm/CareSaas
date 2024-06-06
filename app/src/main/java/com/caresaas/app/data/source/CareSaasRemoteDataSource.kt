package com.caresaas.app.data.source

import com.caresaas.app.data.model.DomainModel
import com.caresaas.app.data.model.DtoModel

interface CareSaasRemoteDataSource {
    suspend fun login(loginRequest : DomainModel.LoginRequest): DtoModel.LoginResponse
    suspend fun getTasks(shortCode: String, careHomeId: String, assignee: String): DtoModel.GetTasksResponse
}