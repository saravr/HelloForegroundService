package com.example.helloforegroundservice

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.helloforegroundservice.service.TimerService
import com.example.helloforegroundservice.service.TimerService.Companion.TIMER_NOTIFICATION_CHANNEL

class HelloForegroundServiceApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                TIMER_NOTIFICATION_CHANNEL,
                "CountDown Timer",
                NotificationManager.IMPORTANCE_HIGH)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        Intent(this, TimerService::class.java).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(it)
            }
        }
    }
}