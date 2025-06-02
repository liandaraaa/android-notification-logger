package com.project.logger.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.logger.data.ApiRequest
import com.project.logger.data.ApiResponse
import com.project.logger.data.di.Repository
import com.project.logger.utils.NetWorkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository, application: Application): BaseViewModel(application) {
    private val _response: MutableLiveData<NetWorkResult<ApiResponse>> = MutableLiveData()
    val response: LiveData<NetWorkResult<ApiResponse>> = _response

    fun submitLogRecord(request: ApiRequest) = viewModelScope.launch {
        repository.submitLogRecord(context, request).collect { values ->
            _response.value = values
        }
    }
}