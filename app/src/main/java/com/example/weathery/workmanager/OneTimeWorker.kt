package com.example.weathery.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.weathery.R
import com.example.weathery.alarm.view.AlarmFragment
import java.util.concurrent.TimeUnit

class OneTimeWorker (context: Context, params: WorkerParameters): Worker(context, params)  {

    @RequiresApi(Build.VERSION_CODES.O)
    override  fun doWork(): Result {

        Log.i("TAM","Courotine ")
        displayNotification("mm")
        AlarmFragment.findNextAlarm()
       /* val request= OneTimeWorkRequest.Builder(OneTimeWorker::class.java).setInitialDelay(1,
            TimeUnit.MINUTES).build()
        this?.let { WorkManager.getInstance(applicationContext).enqueue(request) }*/
      //  val sportApi= RetrofitHelper.getClient()?.create(RetrofitService ::class.java)
       // val responce= sportApi?.getSports()
       /* if (responce != null) {
            if(responce.isSuccessful){

                Log.i("TAG", "WorkManager"+responce.body()?.toString()+"WorkManager")


            }
            else{
                Log.i("TAG", responce.raw().toString()+"Fail")
            }
        }*/
        return  Result.success()
    }
    private fun displayNotification(keyword: String) {

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