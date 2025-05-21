package com.project.logger

import android.app.NotificationManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Secure
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.gson.Gson
import com.project.logger.ui.theme.LoggerTheme

class MainActivity : ComponentActivity() {
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
    }
}

private fun getAllNotification(context:Context){
    val manager:NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val activeNotifications = manager.activeNotifications
    val gson = Gson()
    val notificationString = gson.toJson(activeNotifications.map { it.notification })
    Log.d("ACTIVE_NOTIFICATIONS", notificationString)
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