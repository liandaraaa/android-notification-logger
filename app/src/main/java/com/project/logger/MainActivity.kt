package com.project.logger

import android.app.NotificationManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Secure
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.project.logger.data.ApiRequest
import com.project.logger.data.ApiResponse
import com.project.logger.data.ApiResultHandler
import com.project.logger.entity.NotificationObject
import com.project.logger.entity.toApiRequest
import com.project.logger.event.EventBus
import com.project.logger.ui.theme.LoggerTheme
import com.project.logger.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoggerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        if(!hasNotificationAccess(this)){
            openPermissions(this)
        }
        bindSubscribe()
        observeSubmitLogRecord()
    }

    private fun hasNotificationAccess(context: Context): Boolean {
        return Secure.getString(
            context.applicationContext.contentResolver,
            "enabled_notification_listeners"
        ).contains(context.applicationContext.packageName)
    }

    private fun openPermissions(context: Context) {
        try {
            val settingsIntent =
                Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            context.startActivity(settingsIntent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun bindSubscribe() {
        lifecycleScope.launch {
            EventBus.events.collectLatest { message ->
                val gson = Gson()
                val notification = gson.fromJson(message, NotificationObject::class.java)
                mainViewModel.submitLogRecord(notification.toApiRequest())
            }
        }
    }

    private fun observeSubmitLogRecord() {
        try {
            mainViewModel.response.observe(this) { response ->
                val apiResultHandler = ApiResultHandler<ApiResponse>(this,
                    onLoading = {
                        Log.d("ACTIVE_NOTIFICATION","On loading..")
                    },
                    onSuccess = { data ->
                        Log.d("ACTIVE_NOTIFICATION","On success.. ${data?.data}")
                    },
                    onFailure = {
                        Log.d("ACTIVE_NOTIFICATION","On failure..")
                    })
                apiResultHandler.handleApiResult(response)
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoggerTheme {
        Greeting("Android")
    }
}