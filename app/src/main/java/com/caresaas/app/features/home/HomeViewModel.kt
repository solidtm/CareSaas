package com.caresaas.app.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caresaas.app.core.ResultState
import com.caresaas.app.data.model.DomainModel
import com.caresaas.app.data.model.DtoModel
import com.caresaas.app.domain.repository.CareSaasRepository
import com.caresaas.app.util.helpers.ErrorHelper
import com.caresaas.app.util.helpers.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val careSaasRepository: CareSaasRepository
) : ViewModel() {

    val taskDetails = MutableLiveData<SingleEvent<ResultState<DomainModel.GetTasksResponse>>>()
    val medicationTasks = MutableLiveData<MutableList<DtoModel.TasksData>>()
    val activitiesTasks = MutableLiveData<MutableList<DtoModel.TasksData>>()

    fun getTasks(shortCode: String, careHomeId: String, assignee: String){
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            val message = ErrorHelper.handleException(throwable)
            taskDetails.postValue(SingleEvent(ResultState.Error(message)))
        }

        viewModelScope.launch(coroutineExceptionHandler) {
            taskDetails.postValue(SingleEvent(ResultState.Loading()))
            val response = careSaasRepository.getTasks(shortCode, careHomeId, assignee)
            if (response.code == 201 || response.code == 200){
                taskDetails.postValue(SingleEvent(ResultState.Success(response)))
            }else{
                taskDetails.postValue(SingleEvent(ResultState.Error(response.message)))
            }
        }
    }
}