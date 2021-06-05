package org.sairaa.backgroundservices

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class TestWorker(context: Context, workerParams: WorkerParameters) :Worker(context, workerParams) {

    override fun doWork(): Result {
        val appContext = applicationContext
        return try {

            val resourceUri = inputData.getString(KEY_IMAGE_URI)

            makeStatusNotification("Output is $resourceUri", appContext)

            for (i in 1..10000){
                println("-----i: $i")
                if(isStopped)
                    break
                updateNotification(i.toString(),appContext)
                sleep()
            }

            Result.success()
        } catch (throwable: Throwable) {
            Log.e("-------throwable", "Error applying blur"+throwable.message)
            Result.failure()
        }
    }
}