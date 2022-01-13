package com.example.myservices

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import kotlinx.coroutines.*

class MyJobService : JobService() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    private fun log(string: String) {
        Log.d("MyJobService", string)
    }


    override fun onStartJob(p0: JobParameters?): Boolean {
        log("onStartJob")
        coroutineScope.launch {
            for (i in 1 until 100) {
                delay(1000)
                log("$i")
            }
            //jobFinished - ручная остановка сервиса
            //после нормального завершения работы нужно перезапустить сервис -> true
            jobFinished(p0, true)
        }
        //true - сервис всё ещё работает, т.к. запущен из корутины.
        return true
    }

    //вызывается если система убила сервис
    override fun onStopJob(p0: JobParameters?): Boolean {
        //true - нужно перезапустить
        return true
    }

    companion object {
        const val JOB_ID = 1
    }
}