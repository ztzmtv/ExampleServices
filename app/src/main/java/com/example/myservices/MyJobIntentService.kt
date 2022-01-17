package com.example.myservices

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class MyJobIntentService : JobIntentService() {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }


    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(string: String) {
        Log.d("MyJobIntentService", string)
    }

    override fun onHandleWork(intent: Intent) {
        val page = intent.getIntExtra(PAGE, 0)
        log("onHandleIntent")
        for (i in 1 until 5) {
            Thread.sleep(1000)
            log("$i $page")
        }
    }


    companion object {
        private const val JOB_ID = 111
        private const val PAGE = "page"

        fun enqueue(context: Context, page: Int) {
            enqueueWork(
                context,
                MyJobIntentService::class.java,
                JOB_ID,
                newIntent(context, page)
            )
        }

        fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyJobIntentService::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }
}