package com.example.demonotification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var button1=intent?.getIntExtra(MainActivity.EXTRA_NOTIFICATION_ID,-1).toString()
        Toast.makeText(context,button1,Toast.LENGTH_LONG).show()
    }
}
