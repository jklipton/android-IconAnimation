package com.example.kon7865.shakeanimation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.graphics.BitmapFactory
import android.view.MotionEvent
import java.util.*


class AnimationView(context: Context) : View(context) {

    //all icons in array
    val icon1 = Icon(getBitmapFromResources(R.drawable.heart), 100f, 100f)
    val icon2 = Icon(getBitmapFromResources(R.drawable.heart), 250f, 100f)
    val icon3 = Icon(getBitmapFromResources(R.drawable.heart), 400f, 100f)
    val icon4 = Icon(getBitmapFromResources(R.drawable.heart), 100f, 400f)
    val icon5 = Icon(getBitmapFromResources(R.drawable.heart), 250f, 400f)

    val iconList = ArrayList<Icon>()

    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)
        this.setOnTouchListener { v, event -> handleMotionEvent(event)}

        iconList.add(icon1)
        iconList.add(icon2)
        iconList.add(icon3)
        iconList.add(icon4)
        iconList.add(icon5)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.MAGENTA)

        for (a: Icon in iconList) {
            a.animate()
            a.onDraw(canvas)
        }

        postInvalidateDelayed(1000 / 30)
    }

    private fun handleMotionEvent( motionEvent: MotionEvent ):Boolean {
        //loop through array of icons, check if x and y in 100px for each icon1
        for (a: Icon in iconList) {
            a.runAnimation = (Math.abs(motionEvent.x - a.xp - 50) < 100 && Math.abs(motionEvent.y - a.yp - 50) < 100)
        }
        return true
    }


    //can be in a seperate file
    class Icon( val image : Bitmap, val xp : Float, val yp : Float) {

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

    //index name (R.drawable.name) is a standin for resource ID int, gets compiled at build time
    fun getBitmapFromResources(resourceId: Int) : Bitmap {
        return BitmapFactory.decodeResource(getResources(), resourceId)
    }
}