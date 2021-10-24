package com.rusdevapp.spaceship.elements

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import com.rusdevapp.spaceship.App
import java.util.*

class Asteroid (app: App, res: Resources, drawable: Int) {
    private var asteroid: Bitmap
    private val random: Random
    var shift_x: Int = 0
    var shift_y: Int = 0
    var step_of_shift_x: Int = 0

    init {
        asteroid = BitmapFactory.decodeResource(res, drawable)
        //---
        val coefficient: Int = app.getCoeffForAsteroid()
        val width: Int = asteroid.width/coefficient
        val height: Int = asteroid.height/coefficient
        //---
        random = Random()
        shift_x = app.getWidthOfScreen()
        shift_y = random.nextInt(app.getHeightOfScreen()-height)
        step_of_shift_x = (random.nextInt(25)+15)
        //---
        asteroid = Bitmap.createScaledBitmap(asteroid, width, height, false)
    }

    fun getAsteroid()=asteroid

    fun getWidthOfAsteroid()=asteroid.width

    fun getHeightOfAsteroid()=asteroid.height

    fun getShapeForCheckCollision()=Rect(shift_x, shift_y,
                                    shift_x+asteroid.width, shift_y+asteroid.height)
}