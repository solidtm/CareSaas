package com.caresaas.app.data.api

import com.caresaas.app.data.model.DomainModel
import com.caresaas.app.data.model.DtoModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CareSaasService {

    @POST("auth/login")
    suspend fun login(@Body body: DomainModel.LoginRequest): DtoModel.LoginResponse

    @GET("tasks/{shortCode}/careHome/{careHomeId}")
    suspend fun getTasks(@Path("shortCode") shortCode: String, @Path("careHomeId") careHomeId: String, @Query("assignee") assignee: String): DtoModel.GetTasksResponse
}