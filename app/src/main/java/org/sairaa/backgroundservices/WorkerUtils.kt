/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:JvmName("WorkerUtils")

package org.sairaa.backgroundservices

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


fun makeStatusNotification(message: String, context: Context) {

    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    // Create the notification
    val builder = getNotificationBuilder(message,context)

    // Show the notification
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}

fun getNotificationBuilder(message: String,context: Context):NotificationCompat.Builder{

    val intent = Intent(context, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    val pendingIntent =
        PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
    return NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(NOTIFICATION_TITLE)
        .setContentText(message)
        .setOngoing(true)
        .setContentIntent(pendingIntent)
        .setAutoCancel(false)
        .setOnlyAlertOnce(true)
        .setPriority(NotificationCompat.PRIORITY_MIN)
        .setVibrate(LongArray(0))
}

fun updateNotification(message: String, context: Context){
    val builder = getNotificationBuilder(message,context)
    builder.setContentText(message)
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}

fun cancelStatusNotification(context: Context) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

    notificationManager?.cancel(NOTIFICATION_ID)
}



/**
 * Method for sleeping for a fixed about of time to emulate slower work
 */
fun sleep() {
    try {
        Thread.sleep(DELAY_TIME_MILLIS, 0)
    } catch (e: InterruptedException) {
        println("-----error"+e.message)
    }

}

fun getServiceNotificationBuilder(context: Context): NotificationCompat.Builder{
    val notificationIntent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context,
        0, notificationIntent, 0
    )

    return  NotificationCompat.Builder(context, CHANNEL_ID_G)
        .setContentTitle("Example Service")
        .setContentText("input")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentIntent(pendingIntent)
}

fun updateServiceNotification(message: String, context: Context){
    val builder = getServiceNotificationBuilder(context)
    builder.setContentText(message)
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID_G, builder.build())
}

fun cancelServiceStatusNotification(context: Context) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

    notificationManager?.cancel(NOTIFICATION_ID_G)
}



