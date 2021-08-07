package com.example.demonotification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.app.TaskStackBuilder
import com.example.demonotification.MyApplication.Companion.CHANNEL_ID_1
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("RemoteViewLayout", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonStartAlerActivity.setOnClickListener {
            startActivity(Intent(this, Activity1::class.java))
        }
        buttonShowNotification.setOnClickListener {
            val snoozeIntent = Intent(this, MyBroadcastReceiver::class.java).apply {
                action = "ACTION_SNOOZE"
                putExtra(EXTRA_NOTIFICATION_ID, 0)
            }
            val snoozePendingIntent: PendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0)
            val intentAlert = Intent(this, Activity1::class.java)
            var pendingIntent = TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(intentAlert)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }
//            var pendingIntent=PendingIntent.getActivity(this,0,intent,0)
            var pedingIntentPre = PendingIntent.getActivity(this, 0, intent, 0)
            var pedingIntentPause = PendingIntent.getActivity(this, 0, intent, 0)
            var pedingIntentNext = PendingIntent.getActivity(this, 0, intent, 0)
            var message1 = NotificationCompat.MessagingStyle.Message("Message1", 1000, "Nam")
            var message2 = NotificationCompat.MessagingStyle.Message("Message2", 1000, "Tuan")
            val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_stat)
                .setContentTitle(getString(R.string.content_title_weather))
                .setContentText(getString(R.string.content_text_weather))
//                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.thiennhien))
                .setStyle(
                    NotificationCompat.BigTextStyle().bigText(
                        getString(R.string.content_text_weather)
                    )
                )
//                .addAction(R.drawable.ic_stat, "Pre", pedingIntentPre)
//                .addAction(R.drawable.ic_stat, "Pause", pedingIntentPause)
//                .addAction(R.drawable.ic_stat, "Next", pedingIntentNext)
//                .setStyle(NotificationCompat.MessagingStyle("Message").addMessage(message1).addMessage(message2))
//                .setStyle(NotificationCompat.InboxStyle().addLine("Dong 1").addLine("Dong 2"))
//                .setStyle(
//                    NotificationCompat.BigPictureStyle()
//                        .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.thiennhien))
//                        .bigLargeIcon(null)
//                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)//tự động Cancel khi người dùng click vào thông báo
//                .addAction(R.drawable.ic_stat_name, "Button1", snoozePendingIntent)
            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(getNotificationId().toInt(), builder.build())
            }

        }
        buttonAddRelyButton.setOnClickListener {
            val replyLabel: String = resources.getString(R.string.reply_label)
            val remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
                setLabel(replyLabel)
                build()
            }
            val replyIntent = Intent(this, ReplyBroadCast::class.java).apply {
                putExtra("REPLY", "REPLY")
            }
            val replyPendingIntent: PendingIntent =
                PendingIntent.getBroadcast(
                    this,
                    0,
                    replyIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            var action: NotificationCompat.Action = NotificationCompat.Action.Builder(
                R.drawable.ic_stat_name,
                getString(R.string.reply_label), replyPendingIntent
            )
                .addRemoteInput(remoteInput)
                .build()
            val newMessageNotification = NotificationCompat.Builder(this, CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Reply")
                .setContentText("Reply Content")
                .addAction(action)
                .setAutoCancel(true)
                .build()
            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(notificationId, newMessageNotification)
            }
        }
        buttonShowProgressBar.setOnClickListener {
            val PROGRESS_MAX = 100
            val PROGRESS_CURRENT = 20
            val builder = NotificationCompat.Builder(this, CHANNEL_ID_1).apply {
                setContentTitle("Picture Download")
                setContentText("Download in progress")
                setSmallIcon(R.drawable.ic_stat_name)
                setPriority(NotificationCompat.PRIORITY_LOW)
            }
            NotificationManagerCompat.from(this).apply {
                builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
                notify(getNotificationId().toInt(), builder.build())
//                builder.setContentText("Download complete")
//                    .setProgress(0, 0, true)
//                notify(getNotificationId().toInt(), builder.build())
            }
        }
        buttonImportAcivity.setOnClickListener {
            val fullScreenIntent = Intent(this, ImportantActivity::class.java)
            val fullScreenPendingIntent = PendingIntent.getActivity(
                this, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            var builderFullScreen = NotificationCompat.Builder(this, CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("My Notification")
                .setContentText("Hello Word")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setFullScreenIntent(fullScreenPendingIntent, true)
            with(NotificationManagerCompat.from(this))
            {
                notify(getNotificationId().toInt(), builderFullScreen.build())
            }
        }
        buttonSpecialActivity.setOnClickListener {
            var intent = Intent(this, Activity2::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            var pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            var builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("My Notification")
                .setContentText("Hello")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
            with(NotificationManagerCompat.from(this))
            {
                notify(getNotificationId().toInt(), builder.build())
            }
        }
        buttonMediaActivity.setOnClickListener {
            var image = BitmapFactory.decodeResource(resources, R.drawable.thiennhien)
            var mediaSession = MediaSessionCompat(this, "tag")
            var builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
                .setSubText("A")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentTitle("Wonderful music")
                .setContentText("My Awesome Band")
                .addAction(R.drawable.ic_pre, "Previous", null) // #0
                .addAction(R.drawable.ic_pause, "Pause", null) // #1
                .addAction(R.drawable.ic_next, "Next", null) // #2
                .setLargeIcon(image)
                .setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2 /* #1: pause button \*/)
                        .setMediaSession(mediaSession.getSessionToken())
                )
            with(NotificationManagerCompat.from(this))
            {
                notify(getNotificationId().toInt(), builder.build())
            }
        }
        buttonGroupNotification.setOnClickListener {
            val SUMMARY_ID = getNotificationId().toInt()
            val GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL"

            val newMessageNotification1 =
                NotificationCompat.Builder(this@MainActivity, CHANNEL_ID_1)
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setContentTitle("Title Group 1")
                    .setContentText("Content Group 1")
                    .setGroup(GROUP_KEY_WORK_EMAIL)
                    .build()

            val newMessageNotification2 =
                NotificationCompat.Builder(this@MainActivity, CHANNEL_ID_1)
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setContentTitle("Title Group 2")
                    .setContentText("Content Group 2")
                    .setGroup(GROUP_KEY_WORK_EMAIL)
                    .build()

            val summaryNotification = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID_1)
                .setContentTitle("SummaryTitle")
                //set content text to support devices running API level < 24
                .setContentText("Two new messages")
                .setSmallIcon(R.drawable.ic_stat_name)
                //build summary info into InboxStyle template
                .setStyle(
                    NotificationCompat.InboxStyle()
                        .addLine("Alex Faarborg Check this out")
                        .addLine("Jeff Chang Launch Party")
                        .setBigContentTitle("2 new messages")
                        .setSummaryText("janedoe@example.com")
                )
                //specify which group this notification belongs to
                .setGroup(GROUP_KEY_WORK_EMAIL)
                //set this notification as the summary for the group
                .setGroupSummary(true)
                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
                .build()

            NotificationManagerCompat.from(this).apply {
                notify(emailNotificationId1, newMessageNotification1)
                notify(emailNotificationId2, newMessageNotification2)
                notify(SUMMARY_ID, summaryNotification)
            }
        }
        buttonCustomNotification.setOnClickListener {
            val notificationLayoutCollapse = RemoteViews(packageName, R.layout.custom_colapse)
            val notificationLayoutExpended = RemoteViews(packageName, R.layout.custom_expands)
            notificationLayoutCollapse.setTextViewText(
                R.id.textViewTitle,
                getString(R.string.content_title_weather)
            )
            notificationLayoutCollapse.setTextViewText(
                R.id.textViewContent,
                getString(R.string.content_text_weather)
            )
            notificationLayoutExpended.setTextViewText(
                R.id.textViewTitle,
                getString(R.string.content_title_weather)
            )
            notificationLayoutExpended.setTextViewText(
                R.id.textViewContent,
                getString(R.string.content_text_weather)
            )
            notificationLayoutExpended.setImageViewBitmap(
                R.id.imageViewCustomNotificationExpended,
                BitmapFactory.decodeResource(resources, R.drawable.thiennhien)
            )
            val sdf = SimpleDateFormat("HH:mm:ss")
            val str = sdf.format(Date())
            notificationLayoutCollapse.setTextViewText(R.id.textViewTime, str)
            val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayoutCollapse)
                .setCustomBigContentView(notificationLayoutExpended)
            NotificationManagerCompat.from(this).apply {
                notify(getNotificationId().toInt(), builder.build())
            }
        }
    }

    companion object {
        val KEY_TEXT_REPLY = "key_text_reply"
        var notificationId = 1
        val EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID"
        val emailNotificationId1 = 2
        val emailNotificationId2 = 3
        fun getNotificationId() = Date().time
    }
}
