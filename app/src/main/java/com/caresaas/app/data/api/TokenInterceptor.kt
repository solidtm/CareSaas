package com.caresaas.app.data.api

import com.caresaas.app.util.helpers.CareSaasSharePreference
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(private val sharePreference: CareSaasSharePreference) : Interceptor {

    private var token: String? = null

    fun setToken(token: String?) {
        this.token = token
    }


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            if (token.isNullOrEmpty()) token = sharePreference.token
            var request = chain.request()
            if (request.header("No-Authentication") == null) {
                if (!token.isNullOrEmpty()) {
                    request = request.newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                }
            }
            return chain.proceed(request)
        } catch (e: IOException) {
            // Handle the exception and print an error message
            e.printStackTrace()
            throw e // rethrow the exception if needed
        }
    }

}