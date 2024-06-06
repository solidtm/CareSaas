package com.caresaas.app.features.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caresaas.app.core.ResultState
import com.caresaas.app.data.api.TokenInterceptor
import com.caresaas.app.data.model.DomainModel
import com.caresaas.app.domain.repository.CareSaasRepository
import com.caresaas.app.util.helpers.ErrorHelper
import com.caresaas.app.util.helpers.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val careSaasRepository: CareSaasRepository,
    private val interceptor: TokenInterceptor
) : ViewModel() {

    val loginDetails = MutableLiveData<SingleEvent<ResultState<DomainModel.LoginResponse>>>()
    fun login(loginRequest: DomainModel.LoginRequest){
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            val message = ErrorHelper.handleException(throwable)
            loginDetails.postValue(SingleEvent(ResultState.Error(message)))
        }

        viewModelScope.launch(coroutineExceptionHandler) {
            loginDetails.postValue(SingleEvent(ResultState.Loading()))
            val response = careSaasRepository.login(loginRequest)
            if (response.code == 201 || response.code == 200){
                interceptor.setToken(response.data.userToken.accessToken)
                loginDetails.postValue(SingleEvent(ResultState.Success(response)))
            }else{
                loginDetails.postValue(SingleEvent(ResultState.Error(response.message ?: "Some error occurred")))
            }
        }
    }
}