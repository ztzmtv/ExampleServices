package com.example.myservices

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class SimpleService : Service() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        coroutineScope.launch {
            for (i in 1 until 100) {
                delay(1000)
                log("$i")
            }
        }
        log("onStartCommand")
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    private fun log(string: String) {
        Log.d("SimpleService", string)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SimpleService::class.java)
        }
    }

}