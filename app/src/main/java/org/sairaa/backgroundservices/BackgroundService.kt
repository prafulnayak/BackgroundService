package org.sairaa.backgroundservices

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService

class BackgroundService: JobIntentService() {

    companion object{
        const val TAG = "BackgroundService"
        var shouldIStop = false
        var context: Context? = null
        fun enqueWork(contex: Context,work:Intent){
            context=contex
            enqueueWork(contex,BackgroundService::class.java,123,work)
        }
        fun stopServicees(){
            shouldIStop = true
        }
    }
    override fun onHandleWork(intent: Intent) {

        val s: String? = intent.getStringExtra("inputString")
        s?.let { App().makeStatusNotification(it, context) }
        while (!shouldIStop){
            Thread.sleep(2222)
            println("---------running")
        }
        App().cancelStatusNotification(context)
        println("---------stopped")

    }
}