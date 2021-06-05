package org.sairaa.backgroundservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import java.util.concurrent.Executors

class GeneralServices : Service() {

    companion object{
        var shouldIStop = false
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val appContext = applicationContext
//        makeStatusNotification("processing", appContext)
//        val notificationIntent = Intent(this, MainActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            0, notificationIntent, 0
//        )

        val notificationBuilder = getNotificationBuilder("hello ",appContext).build()

        startForeground(1,notificationBuilder)
        val singleT =
            Executors.newSingleThreadExecutor()
        singleT.execute {
            for (i in 0..9999) {
                Log.i("-----i", ": $i")
                if (singleT.isShutdown) break
                if (shouldIStop) {
                    singleT.shutdown()
                    stopSelf()
                    break
                } else {
                    updateNotification(i.toString(),appContext)
                }
                try {
                    Thread.sleep(1000)
                } catch (ignored: Exception) {
                    ignored.printStackTrace()
                }
            }
        }
//        for (i in 1..10000){
//            println("-----i: $i")
//            if(shouldIStop){
////                withContext(Dispatchers.Main){
//                    stopSelf()
////                }
//                break
//            }
////            withContext(Dispatchers.Main){
//                updateNotification(i.toString(),appContext)
////            }
////            delay(1000)
//            sleep()
//        }

//        try {
//        CoroutineScope(Dispatchers.Main).launch {
//            withContext(Dispatchers.IO){
//                for (i in 1..10000){
//                    println("-----i: $i")
//                    if(shouldIStop){
//                        withContext(Dispatchers.Main){
//                            stopSelf()
//                        }
//                        break
//                    }
//                    withContext(Dispatchers.Main){
//                        updateNotification(i.toString(),appContext)
//                    }
//                    delay(1000)
//                }
//            }
//
//        }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

//        stopSelf()
        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("---","onDestroy")
    }
}