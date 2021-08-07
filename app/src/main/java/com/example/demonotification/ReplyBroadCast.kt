package com.example.demonotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput

class ReplyBroadCast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            var string = RemoteInput.getResultsFromIntent(intent)
                ?.getCharSequence(MainActivity.KEY_TEXT_REPLY).toString()
            Toast.makeText(context, string, Toast.LENGTH_LONG).show()
        }
        if (context != null) {
            NotificationManagerCompat.from(context).cancel(MainActivity.notificationId)
        }
    }

}
