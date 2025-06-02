package com.project.logger.data

import com.project.logger.entity.NotificationObject

data class ApiResponse(
    val checked: Boolean,
    val collectionId: String,
    val collectionName: String,
    val created: String,
    val data: NotificationObject,
    val id: String,
    val updated: String
)