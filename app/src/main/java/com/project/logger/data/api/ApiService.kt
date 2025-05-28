package com.project.logger.data.api

import com.project.logger.data.ApiRequest
import com.project.logger.data.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("collections/log_android/records")
    suspend fun submitLogRecord(@Body data:ApiRequest):Response<ApiResponse>
}