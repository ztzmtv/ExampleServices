package com.example.myservices

import android.content.Context
import android.util.Log
import androidx.work.*

class MyWorker(
    context: Context,
    private val workerParameters: WorkerParameters
) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        val page = workerParameters.inputData.getInt(PAGE, 0)
        log("doWork")
        for (i in 1 until 5) {
            Thread.sleep(1000)
            log("$i $page")
        }
        return Result.success()
    }

    private fun log(string: String) {
        Log.d("MyWorker", string)
    }

    companion object {
        private const val PAGE = "page"
        const val WORK_NAME = "work name"

        fun makeRequest(page: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorker>().apply {
                setInputData(workDataOf(PAGE to page))
                setConstraints(makeConstraints())
            }.build()
        }

        private fun makeConstraints() = Constraints.Builder()
            .setRequiresCharging(true)
            .build()
    }
}