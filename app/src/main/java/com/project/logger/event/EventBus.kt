package com.project.logger.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object EventBus {

    private val _events = MutableSharedFlow<String>()
    val events = _events.asSharedFlow()

    suspend fun publish(event: String) {
        _events.emit(event)
    }
}