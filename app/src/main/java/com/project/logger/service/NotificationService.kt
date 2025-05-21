package com.project.logger.service

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.gson.Gson
import com.project.logger.entity.NotificationObject
import com.project.logger.entity.getAppNameFromPackage

class NotificationService : NotificationListenerService() {

    private var context:Context? = null

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d("ACTIVE_NOTIFICATION","On Connected")
    }

    override fun onListenerDisconnected() {
        Log.d("ACTIVE_NOTIFICATION","On Disconnected")
        super.onListenerDisconnected()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
       try {
           val notification = Gson().toJson(sbn?.notification)
           val extras = Gson().toJson(getNotificationExtras(sbn))
           Log.d("ACTIVE_NOTIFICATION","On Posted ${notification}")
           Log.d("ACTIVE_NOTIFICATION","On Posted Extras ${extras}")
       }catch (e:Exception){
           Log.d("ACTIVE_NOTIFICATION","Error On Posted",e)
       }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        try {
            val notification = Gson().toJson(sbn?.notification)
            val extras = Gson().toJson(getNotificationExtras(sbn))
            Log.d("ACTIVE_NOTIFICATION","On Removed ${notification}")
            Log.d("ACTIVE_NOTIFICATION","On Removed Extras ${extras}")
        }catch (e:Exception){
            Log.d("ACTIVE_NOTIFICATION","Error On Removed",e)
        }
    }

    private fun getNotificationExtras(sbn: StatusBarNotification?):NotificationObject{
        val extras = sbn?.notification?.extras
        var notification = NotificationObject()
        val appName = context?.let { getAppNameFromPackage(it,sbn?.packageName.nullToEmptyString()) }
        notification.appName = appName.orEmpty()
        if(extras !== null){
            val title = extras.getCharSequence(NotificationCompat.EXTRA_TITLE).nullToEmptyString()
            val titleBig = extras.getCharSequence(NotificationCompat.EXTRA_TITLE_BIG).nullToEmptyString()
            val text = extras.getCharSequence(NotificationCompat.EXTRA_TEXT).nullToEmptyString()
            val textBig = extras.getCharSequence(NotificationCompat.EXTRA_BIG_TEXT).nullToEmptyString()
            val infoText = extras.getCharSequence(NotificationCompat.EXTRA_INFO_TEXT).nullToEmptyString()
            val subText = extras.getCharSequence(NotificationCompat.EXTRA_SUB_TEXT).nullToEmptyString()
            val summary = extras.getCharSequence(NotificationCompat.EXTRA_SUMMARY_TEXT).nullToEmptyString()
            val largeIcon = extras.getCharSequence(NotificationCompat.EXTRA_LARGE_ICON).nullToEmptyString()
            val largeIconBig = extras.getCharSequence(NotificationCompat.EXTRA_LARGE_ICON_BIG).nullToEmptyString()
            val smallIcon = extras.getCharSequence(NotificationCompat.EXTRA_SMALL_ICON).nullToEmptyString()
           notification = NotificationObject(appName.orEmpty(),title,titleBig,text,textBig,infoText,subText,summary,largeIcon,largeIconBig,smallIcon)
        return notification
        }
        return notification
    }

    private fun CharSequence?.nullToEmptyString():String {
        if(this == null) {
            return "";
        } else {
            return this.toString();
        }
    }

    override fun onNotificationRankingUpdate(rankingMap: RankingMap?) {
        try {
            Log.d("ACTIVE_NOTIFICATION","On Rank Update")
        }catch (e:Exception){
            Log.d("ACTIVE_NOTIFICATION","Error On Rank Update",e)
        }
    }

}