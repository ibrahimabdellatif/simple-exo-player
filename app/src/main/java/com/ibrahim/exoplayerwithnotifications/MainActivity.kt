package com.ibrahim.exoplayerwithnotifications

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util

class MainActivity : AppCompatActivity() {
    lateinit var playerView: PlayerView

    var player : SimpleExoPlayer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent = Intent(this , AudioPlayerService::class.java)
        Util.startForegroundService(this , intent)

        playerView = findViewById(R.id.exo_view)
    }

//    override fun onStart() {
//        super.onStart()
//
//        var quranUrl = "https://server8.mp3quran.net/ahmad_huth/001.mp3"
//        val mediaItem = MediaItem.fromUri(quranUrl)
//        val trackSelector = DefaultTrackSelector(this).apply {
//            setParameters(buildUponParameters().setMaxVideoSizeSd())
//        }
//        player = SimpleExoPlayer.Builder(this)
//            .setTrackSelector(trackSelector)
//            .build()
//
//        player = SimpleExoPlayer.Builder(this)
//            .build().also { exoPlayer ->
//                playerView.player  = exoPlayer
//
//                exoPlayer.setMediaItem(mediaItem)
//                exoPlayer.playWhenReady = true
//                exoPlayer.seekTo(0 , 0L)
//                exoPlayer.prepare()
//            }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        playerView.player = null
//        player?.release()
//        player = null
//    }
}