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
    private var asteroid1: Bitmap
    private var asteroid2: Bitmap
    private var asteroid3: Bitmap
    private var asteroid4: Bitmap
    private var matrix1: Matrix
    private var matrix2: Matrix
    private var matrix3: Matrix
    private var matrix4: Matrix
    private var asteroidSwitch: Int = 1
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
        //---
        matrix1 = Matrix()
        matrix1.postRotate(0f)
        matrix2 = Matrix()
        matrix2.postRotate(90f)
        matrix3 = Matrix()
        matrix3.postRotate(180f)
        matrix4 = Matrix()
        matrix4.postRotate(270f)
        //---
        asteroid1 = Bitmap.createBitmap(asteroid, 0, 0, width, height, matrix1, false)
        asteroid2 = Bitmap.createBitmap(asteroid, 0, 0, width, height, matrix2, false)
        asteroid3 = Bitmap.createBitmap(asteroid, 0, 0, width, height, matrix3, false)
        asteroid4 = Bitmap.createBitmap(asteroid, 0, 0, width, height, matrix4, false)
    }

    fun getAsteroid():Bitmap
    {
        when(asteroidSwitch)
        {
            1 -> {asteroidSwitch++
                  return asteroid1}
            2 -> {asteroidSwitch++
                  return asteroid2}
            3 -> {asteroidSwitch++
                  return asteroid3}
            else -> {asteroidSwitch++
                  return asteroid4}
        }
    }

    fun getWidthOfAsteroid()=asteroid.width

    fun getHeightOfAsteroid()=asteroid.height

    fun getShapeForCheckCollision()=Rect(shift_x, shift_y,
                                    shift_x+asteroid.width, shift_y+asteroid.height)
}