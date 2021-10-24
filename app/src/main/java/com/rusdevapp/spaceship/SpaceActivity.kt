package com.rusdevapp.spaceship

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics

class SpaceActivity : AppCompatActivity() {

    private lateinit var spaceGame: SpaceGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spaceGame= SpaceGame(context = this, activity = this, supportFragmentManager)
        setContentView(spaceGame)
    }

    override fun onResume():Unit {
        super.onResume()
        spaceGame.resume()
    }

    override fun onPause():Unit {
        super.onPause()
        spaceGame.pause()
    }
}