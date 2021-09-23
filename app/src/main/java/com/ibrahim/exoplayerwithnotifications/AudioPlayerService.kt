package com.ibrahim.exoplayerwithnotifications

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.IBinder
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class AudioPlayerService : Service() {
    var player: SimpleExoPlayer? = null
    lateinit var playerNotificationManager: PlayerNotificationManager

    override fun onCreate() {
        super.onCreate()
        var quranUrl = "https://server8.mp3quran.net/ahmad_huth/001.mp3"
        val mediaItem = MediaItem.fromUri(quranUrl)
        val trackSelector = DefaultTrackSelector(this).apply {
            setParameters(buildUponParameters().setMaxVideoSizeSd())
        }
        player = SimpleExoPlayer.Builder(this)
            .setTrackSelector(trackSelector)
            .build()

        player = SimpleExoPlayer.Builder(this)
            .build().also { exoPlayer ->


                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.playWhenReady = true
//                exoPlayer.seekTo(0 , 0L)
                exoPlayer.prepare()
            }

        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
            applicationContext, PLAYBACK_CHANNEL_ID, R.string.PLAYBACK_NOTIFICATION_NAME,
            R.string.PLAYBACK_NOTIFICATION_ID,
            object : PlayerNotificationManager.MediaDescriptionAdapter {
                override fun getCurrentContentTitle(player: Player): CharSequence {
                    return "title"
                }

                override fun createCurrentContentIntent(player: Player): PendingIntent? {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    return PendingIntent.getActivity(
                        applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }

                override fun getCurrentContentText(player: Player): CharSequence? {
                    return "Description"
                }

                override fun getCurrentLargeIcon(
                    player: Player,
                    callback: PlayerNotificationManager.BitmapCallback
                ): Bitmap? {
                    return null
                }
            }, object : PlayerNotificationManager.NotificationListener {
                override fun onNotificationPosted(
                    notificationId: Int,
                    notification: Notification,
                    ongoing: Boolean
                ) {
                    startForeground(notificationId, notification)
                }

                override fun onNotificationCancelled(
                    notificationId: Int,
                    dismissedByUser: Boolean
                ) {
                    stopSelf()
                }
            }
        )
        playerNotificationManager.setPlayer(player)

    }

    override fun onDestroy() {
        playerNotificationManager.setPlayer(null)
        player?.release()
        player = null
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //this to terminat the service when it's be ready
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}