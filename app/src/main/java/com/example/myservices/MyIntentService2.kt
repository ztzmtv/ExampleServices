package com.example.myservices

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log

class MyIntentService2 : IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        setIntentRedelivery(true)
        log("onCreate")
    }


    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(string: String) {
        Log.d("MyIntentService2", string)
    }

    override fun onHandleIntent(intent: Intent?) {
        val page = intent?.getIntExtra(PAGE, 0) ?: 0
        log("onHandleIntent")
        for (i in 1 until 5) {
            Thread.sleep(1000)
            log("$i $page")
        }
    }


    companion object {
        private const val NAME = "Name"
        private const val PAGE = "page"
        fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyIntentService2::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }
}