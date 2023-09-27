package com.example.helloforegroundservice.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.helloforegroundservice.R

class TimerService: Service() {
    private var countdown = 50
    private var timer: CountDownTimer? = null
    private var timeLeft = 0L
    private var notificationBuilder: NotificationCompat.Builder? = null
    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        resetTimer()
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        countdown = 50
        when (intent?.action) {
            ACTIONS.START.toString() -> {
                notificationBuilder = NotificationCompat.Builder(this, TIMER_NOTIFICATION_CHANNEL)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Countdown Timer")
                    .setContentText(timeLeft.toString())
                startForeground(1, notificationBuilder?.build())
                resetTimer()
                timer?.start()
                Log.d(TAG, "++++ STARTED!!!")
            }
            ACTIONS.STOP.toString() -> {
                timer?.cancel()
                Log.d(TAG, "++++ STOPPED!!!")
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun stopService(name: Intent?): Boolean {
        timer?.cancel()
        timer = null
        return super.stopService(name)
    }

    enum class ACTIONS {
        START,
        STOP
    }

    private fun resetTimer() {
        timer?.cancel()
        timer = null
        timer = object: CountDownTimer(countdown * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                notificationBuilder?.setSilent(true)
                notificationBuilder?.setContentText(timeLeft.toString())
                notificationManager.notify(1, notificationBuilder?.build())
                Log.d(TAG, "++++ TICK remaining ms: $millisUntilFinished")
            }

            override fun onFinish() {
                Log.d(TAG, "++++ Finished!!!")
            }
        }
    }

    companion object {
        private const val TAG = "TimerService"
        const val TIMER_NOTIFICATION_CHANNEL = "TimerChannel"
    }
}