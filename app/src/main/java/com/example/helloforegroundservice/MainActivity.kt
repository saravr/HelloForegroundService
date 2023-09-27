package com.example.helloforegroundservice

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import com.example.helloforegroundservice.service.TimerService
import com.example.helloforegroundservice.ui.theme.HelloForegroundServiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.POST_NOTIFICATIONS
            ), 0)
        }

        setContent {
            HelloForegroundServiceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            Intent(applicationContext, TimerService::class.java).also {
                                it.action = TimerService.ACTIONS.START.toString()
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    startService(it)
                                }
                            }
                        }) {
                            Text("Start")
                        }
                        Button(onClick = {
                            Intent(applicationContext, TimerService::class.java).also {
                                it.action = TimerService.ACTIONS.STOP.toString()
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    startService(it)
                                }
                            }
                        }) {
                            Text("Stop")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HelloForegroundServiceTheme {
        Greeting("Android")
    }
}