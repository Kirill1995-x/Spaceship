package com.rusdevapp.spaceship

import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics

class App(context: Context) {
    val APP: String = "SpaceshipGame"
    val TIME_FOR_SLEEP: Long = 17
    val TIME_FOR_CLOSE: Long = 500
    val DEFAULT_COLOR: Int = Color.WHITE
    val NEXTLEVEL_COLOR: Int = Color.RED
    val DEFAULT_TEXTSIZE: Float
    val NEXTLEVEL_TEXTSIZE: Float
    val leftVolume: Float = 1f // уровень громкости левого канала
    val rightVolume: Float = 1f //уровень громкости правого канала
    val rate: Float = 1f //скорость воспроизведения
    val placeForTextX: Float = 1/2f //коэффициент для расчета положения текста с очками по оси X
    val placeForTextY: Float = 1/5f //коэффициент для расчета положения текста с очками по оси Y
    val placeForSpaceshipX: Int = 100 //положение коробля по оси X
    val placeForSpaceshipY: Int = 1/2//коэффициент для расчета положения коробля по оси Y
    var flagForChooseSizeOfDevice: String
    val displayMetrics: DisplayMetrics
    //---
    init {
        displayMetrics = context.resources.displayMetrics
        flagForChooseSizeOfDevice = context.resources.getString(R.string.flag)
        DEFAULT_TEXTSIZE = when(flagForChooseSizeOfDevice) {
                                    "small" -> 100f
                                    "large" -> 156f
                                    "xlarge" -> 184f
                                    else -> 128f
                                }
        NEXTLEVEL_TEXTSIZE = when(flagForChooseSizeOfDevice) {
                                    "small" -> 120f
                                    "large" ->176f
                                    "xlarge" -> 204f
                                    else -> 148f
                                }
    }
    //---
    fun getWidthOfScreen() = displayMetrics.widthPixels
    fun getHeightOfScreen() = displayMetrics.heightPixels
    fun getCoeffForAsteroid():Int
    {
        return when(flagForChooseSizeOfDevice) {
            "small" -> 7
            "large" -> 4
            "xlarge" -> 3
            else -> 6
        }
    }
    fun getCoeffForSpaceship():Int
    {
        return when (flagForChooseSizeOfDevice) {
            "small" -> 5
            "large" -> 3
            "xlarge" -> 2
            else -> 4
        }
    }
}