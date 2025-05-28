package com.project.logger.data.di

import android.content.Context
import com.project.logger.data.ApiRequest
import com.project.logger.data.ApiResponse
import com.project.logger.data.RemoteDataSource
import com.project.logger.data.toResultFlow
import com.project.logger.utils.NetWorkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun submitLogRecord(context: Context, request: ApiRequest): Flow<NetWorkResult<ApiResponse>> {
        return toResultFlow(context){
            remoteDataSource.submitLogRecord(request)
        }
    }
}