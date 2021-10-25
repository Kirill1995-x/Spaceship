package com.rusdevapp.spaceship

import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rusdevapp.spaceship.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding
private lateinit var sharedPreferences: SharedPreferences
private lateinit var app: App
private lateinit var soundPool: SoundPool
private var sound: Int = 0

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = App(context = this)
        sharedPreferences = getSharedPreferences(app.APP, MODE_PRIVATE)
        soundPool = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            val audioAttributes: AudioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
            SoundPool.Builder().setAudioAttributes(audioAttributes).build()
        }
        else SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        sound = soundPool.load(this, R.raw.click_button, 1)
        //---
        binding.tvStartGame.setOnClickListener(this)
        //binding.tvChooseSpaceship.setOnClickListener(this)
        binding.tvInstruction.setOnClickListener(this)
        binding.tvAboutAuthors.setOnClickListener(this)
        binding.imgVolume.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        //отображение настройки звука
        if(sharedPreferences.getBoolean("volume", true))
            binding.imgVolume.setImageResource(R.drawable.ic_baseline_volume_up_24)
        else
            binding.imgVolume.setImageResource(R.drawable.ic_baseline_volume_off_24)
        //отображение кол-ва очков
        binding.tvScore.text= (resources.getString(R.string.result)+" "+
                              sharedPreferences.getInt("score", 0)+" "+
                              resources.getString(R.string.km))
        //выбор типа корабля (пока только один вариант)
        sharedPreferences.edit().putInt("spaceship", R.drawable.spaceship1).apply()
    }

    override fun onClick(v: View) {
        when(v.id)
        {
            R.id.tvStartGame -> startGame()
            //R.id.tvChooseSpaceship -> chooseSpaceship()
            R.id.tvInstruction -> instruction()
            R.id.tvAboutAuthors -> showAuthors()
            R.id.imgVolume -> changeVolume()
        }
    }

    private fun startGame():Unit
    {
        if(sharedPreferences.getBoolean("volume", true)){
            soundPool.play(sound, app.leftVolume, app.rightVolume, 0, 0, app.rate)
        }
        startActivity(Intent(this, SpaceActivity::class.java))
    }

    /*private fun chooseSpaceship():Unit
    {
        if(sharedPreferences.getBoolean("volume", true)){
            soundPool.play(sound, app.leftVolume, app.rightVolume, 0, 0, app.rate)
        }
    }*/

    private fun instruction():Unit
    {
        if(sharedPreferences.getBoolean("volume", true)){
            soundPool.play(sound, app.leftVolume, app.rightVolume, 0, 0, app.rate)
        }
        val dialogInstruction: DialogInstruction = DialogInstruction()
        dialogInstruction.show(supportFragmentManager, "InstructionDialog")
    }

    private fun showAuthors():Unit
    {
        if(sharedPreferences.getBoolean("volume", true)){
            soundPool.play(sound, app.leftVolume, app.rightVolume, 0, 0, app.rate)
        }
        startActivity(Intent(this, AboutAuthorsActivity::class.java))
    }

    private fun changeVolume():Unit
    {
        var isVolume: Boolean = sharedPreferences.getBoolean("volume", true)
        isVolume = !isVolume
        if(isVolume){
            soundPool.play(sound, app.leftVolume, app.rightVolume, 0, 0, app.rate)
        }
        sharedPreferences.edit().putBoolean("volume", isVolume).apply()
        if(isVolume) binding.imgVolume.setImageResource(R.drawable.ic_baseline_volume_up_24)
        else binding.imgVolume.setImageResource(R.drawable.ic_baseline_volume_off_24)
    }
}