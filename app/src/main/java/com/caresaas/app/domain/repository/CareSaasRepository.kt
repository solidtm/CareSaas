package com.caresaas.app.domain.repository

import com.caresaas.app.data.model.DomainModel

//here we define abstract functions that represent each use-case in the project

interface CareSaasRepository {
    suspend fun login(loginRequest: DomainModel.LoginRequest): DomainModel.LoginResponse
    suspend fun getTasks(shortCode: String, careHomeId: String, assignee: String): DomainModel.GetTasksResponse
}