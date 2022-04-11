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
import com.example.weathery.model.RepositoryInterface
import com.example.weathery.network.ApiManager
import com.example.weathery.network.WebService
import java.util.concurrent.TimeUnit

class OneTimeWorker (context: Context, params: WorkerParameters):CoroutineWorker(context,params)  {
    override suspend fun doWork(): ListenableWorker.Result {
        val data = inputData
       // val _iRepo: RepositoryInterface = iRepo
        val lat = data.getString("lat")
        val long = data.getString("long")
        val weatherApi= ApiManager.getClient()!!.create(WebService ::class.java)
        // val responce= sportApi?.getWeather2("24.661994","5.167022","","272e72149cbb28b619e0d0a924024a41","metric","ar")
     val responce=   weatherApi.getWeather2(lat,long,"minutely","272e72149cbb28b619e0d0a924024a41","metric","ar")!!

        if (responce != null) {
            if(responce.isSuccessful){

                Log.i("TAG", "WorkManager"+responce.body()?.toString()+"WorkManager")
                val desc=responce.body()?.alerts?.get(0)?.description
                if(desc !=null ){
                    displayNotification(desc)
                }else {
                    displayNotification("No Weather Alert")
                }
            }
            else{
                Log.i("TAG", responce.raw().toString()+"Fail")
            }
        }

        return  ListenableWorker.Result.success()

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
            .setContentTitle("Weather Alert")
            .setContentText("$keyword")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
           // .setContentIntent(contentIntent)
            .setSmallIcon(R.drawable.ic_cloud)
        assert(notificationManager != null)
        notificationManager.notify(1, builder.build())
    }


}