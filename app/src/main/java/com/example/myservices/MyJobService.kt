package com.example.myservices

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.util.Log
import kotlinx.coroutines.*

class MyJobService : JobService() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }


    override fun onStartJob(p0: JobParameters?): Boolean {
        log("onStartJob")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            coroutineScope.launch {
                var workItem = p0?.dequeueWork()
                while (workItem != null) {
                    val page = workItem.intent.getIntExtra(PAGE, 0)
                    for (i in 0 until 5) {
                        delay(1000)
                        log("$page $i")
                    }
                    p0?.completeWork(workItem)
                    workItem = p0?.dequeueWork()
                }
                //jobFinished - ручная остановка сервиса
                //после нормального завершения работы нужно перезапустить сервис -> true
                jobFinished(p0, false)
            }
        }
        //true - сервис всё ещё работает, т.к. запущен из корутины.
        return true
    }

    //вызывается если система убила сервис
    override fun onStopJob(p0: JobParameters?): Boolean {
        //true - нужно перезапустить
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    private fun log(string: String) {
        Log.d("MyJobService", string)
    }

    companion object {
        const val JOB_ID = 1
        private const val PAGE = "page"

        fun newIntent(page: Int): Intent {
            return Intent().apply {
                putExtra(PAGE, page)
            }
        }
    }
}