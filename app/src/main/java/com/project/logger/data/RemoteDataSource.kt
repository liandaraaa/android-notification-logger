package com.project.logger.data

import com.project.logger.data.api.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService){
    suspend fun submitLogRecord(request: ApiRequest) = apiService.submitLogRecord(request)
}