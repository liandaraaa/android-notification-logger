package com.project.logger.entity

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.project.logger.data.ApiRequest

data class NotificationObject(
    var appName:String = "",
    var title:String = "",
    var titleBig:String = "",
    var text:String = "",
    var textBig:String = "",
    var infoText:String = "",
    var subText:String = "",
    var summary:String = "",
    var largeIcon:String = "",
    var largeIconBig:String = "",
    var smallIcon:String  = ""
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

//fun NotificationObject.toApiRequest():ApiRequest{
//    return ApiRequest(
//        appName = appName,
//        title = title,
//        titleBig = titleBig,
//        text = "In text nya dummy",
//        textBig = "In text nya dummy",
//        infoText = "In text nya dummy",
//        subText = "In text nya dummy",
//        summary = "In text nya dummy",
//        largeIcon = largeIcon,
//        largeIconBig = largeIconBig,
//        smallIcon = smallIcon
//    )
//}