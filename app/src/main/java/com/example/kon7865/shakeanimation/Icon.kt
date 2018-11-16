package com.example.kon7865.shakeanimation

import android.graphics.Bitmap
import android.graphics.Canvas
import java.util.*

class Icon(val image : Bitmap, val xp : Float, val yp : Float) {

    val r : Random = Random()
    var finalX : Float = 0f
    var finalY : Float = 0f
    var runAnimation : Boolean = false

    fun animate(){
        finalX = xp + r.nextFloat() * 10f - 5f
        finalY = yp + r.nextFloat() * 10f - 5f
    }

    fun onDraw(canvas: Canvas) {
        if (runAnimation) {
            canvas.drawBitmap(image, finalX, finalY, null)
        }
        else canvas.drawBitmap(image, xp, yp, null)
    }
}