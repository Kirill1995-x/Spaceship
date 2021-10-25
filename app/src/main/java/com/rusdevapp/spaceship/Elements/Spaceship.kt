package com.rusdevapp.spaceship.Elements

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import com.rusdevapp.spaceship.App

class Spaceship (app: App, numberOfSpaceship: Int, res: Resources) {

    private var spaceship: Bitmap
    private val matrix: Matrix
    var shift_x: Int //положение коробля по оси X
    var shift_y: Int //положение коробля по оси Y
    val width: Int //длина коробля
    val height: Int //высота коробля

    init {
        //---
        spaceship = BitmapFactory.decodeResource(res, numberOfSpaceship)
        //---
        val coefficient: Int = app.getCoeffForSpaceship()
        width = spaceship.width/coefficient
        height = spaceship.height/coefficient
        matrix = Matrix()
        matrix.postRotate(90f)
        //---
        spaceship = Bitmap.createScaledBitmap(spaceship, width, height, false)
        spaceship = Bitmap.createBitmap(spaceship, 0, 0, width, height, matrix, false)
        //---
        shift_x = app.placeForSpaceshipX
        shift_y = app.getHeightOfScreen()*app.placeForSpaceshipY
    }

    fun getSpaceship()=spaceship

    fun getShapeForCheckCollision()= Rect(shift_x, shift_y,
                                     shift_x+spaceship.width, shift_y+spaceship.height)

}