package com.project.logger.entity

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

data class NotificationObject(
    var appName:String = "",
    val title:String = "",
    val titleBig:String = "",
    val text:String = "",
    val textBig:String = "",
    val infoText:String = "",
    val subText:String = "",
    val summary:String = "",
    val largeIcon:String = "",
    val largeIconBig:String = "",
    val smallIcon:String  = ""
)

fun getAppNameFromPackage(context:Context,packageName:String):String {
    val pm:PackageManager = context.applicationContext.packageManager;
    var ai:ApplicationInfo? = null
    ai = try {
        pm.getApplicationInfo(packageName, 0);
    } catch(e:PackageManager.NameNotFoundException) {
        null
    }
    return if(ai != null) pm.getApplicationLabel(ai).toString() else packageName
}