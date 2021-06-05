package org.sairaa.backgroundservices

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import java.util.concurrent.TimeUnit

class App: Application() {
    private lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()
    }

    fun makeStatusNotification(message: String, context: Context?) {
        // Make a channel if necessary
        // the NotificationChannel class is new and not in the support library
        val name = "VERBOSE_NOTIFICATION_CHANNEL_NAME"
        val description = "VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION"
        val importance = NotificationManager.IMPORTANCE_HIGH
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,name,importance)
            channel.description = description
            // Add the channel
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            notificationManager?.createNotificationChannel(channel)
            // Create the notification
            val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("NOTIFICATION_TITLE")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(LongArray(0))
            // Show the notification
            NotificationManagerCompat.from(context!!).notify(1, builder.build())
        }

    }

    fun cancelStatusNotification(context: Context?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.cancel(1)
    }

    fun downLoadSyncProcess(context:Context) {
        workManager = WorkManager.getInstance(context)
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)

        val downloadSyncRequest = OneTimeWorkRequestBuilder<TestWorker>()
            .setConstraints(constraints.build())
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS,
                TimeUnit.MICROSECONDS)
            .build()

        workManager.enqueueUniqueWork(
            UNIQUE_PULL_DATA_FROM_SERVER,
            ExistingWorkPolicy.REPLACE,
            downloadSyncRequest)
    }

    fun cancelWork(context: Context) {
        workManager = WorkManager.getInstance(context)
        workManager.cancelAllWork()
        cancelStatusNotification(context)
    }

    companion object {
        private const val PREFS_CURRENT_USER = "current_user"
        private const val  KEY_NOTE_DATA = "KEY_NOTE_DATA"
        private const val IMAGE_UPLOAD_WORK_NAME = "images_upload_work"
        private const val NOTE_UPLOAD_WORK ="notes_upload_work"
        @JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
            "WorkManager Notifications for cloud sync"
        const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
            "Shows notifications whenever work starts"
        @JvmField val NOTIFICATION_TITLE: CharSequence = "Cloud Sync Started"
        const val CHANNEL_ID = "VERBOSE_NOTIFICATION_CLOUD_SYNC"
        const val NOTIFICATION_ID = 191
        private const val UNIQUE_PULL_DATA_FROM_SERVER ="pull data from server"
        const val SYNC_FEATURES = "sync_features"
        const val SYNC_OPTION ="sync_option"
    }

}