package com.rusdevapp.spaceship.elements

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Background(sizeOfScreenX: Int, sizeOfScreenY: Int, res: Resources, drawable: Int)
{
    var shift_x:Float = 0f
    var shift_y:Float = 0f
    private var background: Bitmap

    init {
        background=BitmapFactory.decodeResource(res, drawable)
        background=Bitmap.createScaledBitmap(background, sizeOfScreenX, sizeOfScreenY, false)
    }

    fun getBackground() = background
    fun getWidthOfBackground() = background.width
}