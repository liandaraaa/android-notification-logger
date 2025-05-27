package com.project.logger.entity

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

data class NotificationObject(
    var appName:String = "",
    title:String = "",
    titleBig:String = "",
    text:String = "",
    textBig:String = "",
    infoText:String = "",
    subText:String = "",
    summary:String = "",
    largeIcon:String = "",
    largeIconBig:String = "",
    smallIcon:String  = ""
)

fun getAppNameFromPackage(context:Context,packageName:String):String {
    pm:PackageManager = context.applicationContext.packageManager;
    var ai:ApplicationInfo? = null
    ai = try {
        pm.getApplicationInfo(packageName, 0);
    } catch(e:PackageManager.NameNotFoundException) {
        null
    }
    return if(ai != null) pm.getApplicationLabel(ai).toString() else packageName
}