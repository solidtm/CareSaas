package com.caresaas.app.data.model

import com.google.gson.annotations.SerializedName

object DomainModel {
    data class LoginResponse(
        val code: Int,
        val data: Data,
        val message: String,
        val status: String
    )

    data class Data(
        val user: User,
        val userToken: UserToken
    )

    data class User(
        val email: String,
        val familyName: String,
        val givenName: String,
        val groups: List<String>,
        val name: String,
        val organization: String,
        val preferredUsername: String,
        val userId: String
    )

    data class UserToken(
        val accessToken: String,
        val refreshToken: String
    )

    data class LoginRequest(
        val userName: String,
        val password: String,
    )

    data class GetTasksResponse(
        val code: Int,
        val data: List<DtoModel.TasksData>,
        val message: String,
        val status: String
    )
}