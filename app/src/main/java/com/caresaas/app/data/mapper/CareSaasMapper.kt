package com.caresaas.app.data.mapper

import com.caresaas.app.data.model.DomainModel
import com.caresaas.app.data.model.DtoModel


fun DtoModel.LoginResponse.map(): DomainModel.LoginResponse {
    val userResult = this.data.user
    val user = DomainModel.User(
        email = userResult.email,
        familyName = userResult.familyName,
        givenName = userResult.givenName,
        groups = userResult.groups,
        organization = userResult.organization,
        userId = userResult.userId,
        name = userResult.name,
        preferredUsername = userResult.preferredUsername)

    val userToken = DomainModel.UserToken(accessToken = this.data.userToken.accessToken, refreshToken = this.data.userToken.refreshToken)
    val agentData = DomainModel.Data(
        user = user,
        userToken = userToken,
    )
    return DomainModel.LoginResponse(
        message = message,
        data = agentData,
        status = status,
        code = code
    )
}

fun DtoModel.GetTasksResponse.map(): DomainModel.GetTasksResponse{
    return DomainModel.GetTasksResponse(
        code = this.code,
        data = this.data,
        message = this.message,
        status = this.status
    )
}