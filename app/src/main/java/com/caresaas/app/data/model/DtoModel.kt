package com.caresaas.app.data.model

import com.google.gson.annotations.SerializedName

object DtoModel {

    data class LoginResponse(
        @SerializedName("code") val code: Int,
        @SerializedName("data") val data: Data,
        @SerializedName("message") val message: String,
        @SerializedName("status") val status: String
    )

    data class Data(
        @SerializedName("user") val user: User,
        @SerializedName("userToken") val userToken: UserToken
    )

    data class User(
        @SerializedName("email") val email: String,
        @SerializedName("email_verified") val emailVerified: Boolean,
        @SerializedName("family_name") val familyName: String,
        @SerializedName("given_name") val givenName: String,
        @SerializedName("groups") val groups: List<String>,
        @SerializedName("lastRole") val lastRole: String,
        @SerializedName("name") val name: String,
        @SerializedName("organization") val organization: String,
        @SerializedName("preferred_username") val preferredUsername: String,
        @SerializedName("realm_access") val realmAccess: RealmAccess,
        @SerializedName("sub") val sub: String,
        @SerializedName("userId") val userId: String
    )

    data class RealmAccess(
        @SerializedName("roles") val roles: List<String>
    )

    data class UserToken(
        @SerializedName("access_token") val accessToken: String,
        @SerializedName("refresh_token") val refreshToken: String
    )

    data class GenericResponse(
        @SerializedName("success") val success: Boolean?,
        @SerializedName("statusCode", alternate = ["status_code"]) val statusCode: Int?,
        @SerializedName("message") val message: String?,
    )

    data class GetTasksResponse(
        @SerializedName("code") val code: Int,
        @SerializedName("data") val data: List<TasksData>,
        @SerializedName("message") val message: String,
        @SerializedName("status") val status: String
    )

    data class TasksData(
        @SerializedName("action") val action: String,
        @SerializedName("hourOfDay") val hourOfDay: String,
        @SerializedName("isAssigned") val isAssigned: Boolean,
        @SerializedName("noSupportPersonnel") val noSupportPersonnel: Int,
        @SerializedName("order") val order: String,
        @SerializedName("priority") val priority: String,
        @SerializedName("supportLevel") val supportLevel: String,
        @SerializedName("supportPersonnel") val supportPersonnel: String,
        @SerializedName("taskAssignments") val taskAssignments: List<TaskAssignment>,
        @SerializedName("taskDate") val taskDate: String,
        @SerializedName("taskDetailRef") val taskDetailRef: String,
        @SerializedName("taskEndedOn") val taskEndedOn: Any,
        @SerializedName("taskGroup") val taskGroup: String,
        @SerializedName("taskId") val taskId: String,
        @SerializedName("taskScheduleId") val taskScheduleId: String,
        @SerializedName("taskStartedOn") val taskStartedOn: Any,
        @SerializedName("taskType") val taskType: String,
        @SerializedName("timeOfDay") val timeOfDay: String,
        @SerializedName("userId") val userId: Int,
        @SerializedName("workStatus") val workStatus: Any
    )

    data class TaskAssignment(
        @SerializedName("assignee") val assignee: Assignee,
        @SerializedName("assignmentStatus") val assignmentStatus: Any
    )

    data class Assignee(
        @SerializedName("firstName") val firstName: String,
        @SerializedName("lastName") val lastName: String,
        @SerializedName("userId") val userId: Int
    )
}