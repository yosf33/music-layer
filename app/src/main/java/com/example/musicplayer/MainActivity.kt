package com.example.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var mp: MediaPlayer? = null
    private var currentSong: MutableList<Int> = mutableListOf(R.raw.sky_high)
    lateinit var fab_play: FloatingActionButton
    lateinit var fab_pause: FloatingActionButton
    lateinit var fab_stop: FloatingActionButton
    lateinit var seekbar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_play = findViewById(R.id.fab_play)
        fab_pause = findViewById(R.id.fab_pause)
        fab_stop = findViewById(R.id.fab_stop)
        seekbar = findViewById(R.id.seekbar)

        controlSound(currentSong[0])
    }

    private fun controlSound(id: Int) {
        //if we make start while one player already exit it cause crashes
        fab_play.setOnClickListener {
            if (mp == null) {
                mp = MediaPlayer.create(this, id)
                initialiseSeekBar()
            }
            mp?.start()
        }

        fab_pause.setOnClickListener {
            if (mp !== null) {
                mp?.pause()
            }
        }
        //this to release recourses
        fab_stop.setOnClickListener {
            if (mp !== null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }

            seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                //accept user moving the seek bar
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        mp?.seekTo(progress)

                    }

                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })
        }
    }

    private fun initialiseSeekBar() {
        //this to update seekbar
        seekbar.max = mp!!.duration
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seekbar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    seekbar.progress = 0
                }
            }
        }, 0)

    }

}


