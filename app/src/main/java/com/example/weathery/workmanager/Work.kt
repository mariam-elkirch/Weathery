package com.example.weathery.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weathery.R
import com.example.weathery.alarm.view.AlarmFragment
import com.example.weathery.model.Alarm
import java.util.concurrent.TimeUnit

class Work (context: Context, params: WorkerParameters): Worker(context, params) {
    var mcontext: Context = context

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        Log.i("TAM","ONE Tme")
        AlarmFragment.findNextAlarm()
      //  displayNotification("")
      /*  val request= OneTimeWorkRequest.Builder(OneTimeWorker::class.java).setInitialDelay(1,
            TimeUnit.MINUTES).build()
        this?.let { WorkManager.getInstance(mcontext).enqueue(request) }*/
//      AlarmFragment.findTheRest()
        return Result.success()
    }


    private fun displayNotification(keyword: String) {
    //  Log.i("TAM", alarmList.+"Alarmlisttt")
        val notificationManager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "RefillReminders",
                "RefillReminders", NotificationManager.IMPORTANCE_DEFAULT
            )
            assert(notificationManager != null)
            notificationManager.createNotificationChannel(channel)
        }
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext, "RefillReminders"
        )
            .setContentTitle("$keyword Refill Alert")
            .setContentText("It's time to refill your medication stock")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // .setContentIntent(contentIntent)
            .setSmallIcon(R.drawable.ic_cloud)
        assert(notificationManager != null)
        notificationManager.notify(1, builder.build())
    }
}