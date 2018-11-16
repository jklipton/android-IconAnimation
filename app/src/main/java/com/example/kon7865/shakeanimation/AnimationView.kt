package com.example.kon7865.shakeanimation

import android.content.Context
import android.graphics.*
import android.view.View
import android.view.MotionEvent
import java.util.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.text.style.LineHeightSpan


class AnimationView(context: Context) : View(context) {

    //all icons in array
    //TODO: set floats to be responsive for screen widths

    val iconList = ArrayList<Icon>()

    //canvas variables
    var panelHeight = 0.0f
    var iconWidth = 0
    var iconHeight = 0
    var iconMargin = 0

    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)
        this.setOnTouchListener { v, event -> handleMotionEvent(event)}
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        setCanvasVariables(canvas)
        setIconFloats(canvas)
        //draw each layer in a function, go from furthest back to front
        drawBackground(canvas)
        drawPanels(canvas)


        for (a: Icon in iconList) {
            a.animate()
            a.onDraw(canvas)
        }

        postInvalidateDelayed(1000 / 30)
    }

    private fun setIconFloats(canvas: Canvas) {

        val heartIcon = Icon(getBitmapFromResources(R.drawable.icon_heart), iconMargin.toFloat(), 5f)
        val toiletIcon = Icon(getBitmapFromResources(R.drawable.icon_toilet), 250f, 100f)
        val foodIcon = Icon(getBitmapFromResources(R.drawable.icon_food), 400f, 100f)
        val medsIcon = Icon(getBitmapFromResources(R.drawable.icon_meds), 100f, 400f)
        val moneyIcon = Icon(getBitmapFromResources(R.drawable.icon_money), 250f, 400f)
        val socialIcon = Icon(getBitmapFromResources(R.drawable.icon_social), 250f, 400f)
        val createIcon = Icon(getBitmapFromResources(R.drawable.icon_create), 250f, 400f)
        val settingsIcon = Icon(getBitmapFromResources(R.drawable.icon_settings), 250f, 400f)

        iconList.addAll(Arrays.asList(heartIcon, toiletIcon, foodIcon, medsIcon, moneyIcon, socialIcon, createIcon, settingsIcon))
        invalidate()
    }
    private fun setCanvasVariables(canvas: Canvas) {
        panelHeight = (height / 7).toFloat()
        iconWidth = (width / 2) / 4
        iconMargin = width - (iconWidth * 4) / 4
    }

    private fun handleMotionEvent( motionEvent: MotionEvent ):Boolean {
        //loop through array of icons, check if x and y in 100px for each icon1
        for (a: Icon in iconList) {
            a.runAnimation = (Math.abs(motionEvent.x - a.xp - 50) < 100 && Math.abs(motionEvent.y - a.yp - 50) < 100)
        }
        return true
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawColor(Color.rgb(247, 237, 217))
    }

    private fun drawPanels(canvas: Canvas){
        var paintPink = Paint()
        paintPink.color = Color.MAGENTA
        canvas.drawRect(0f, 0f, width.toFloat(), panelHeight, paintPink)

        var paintBlue = Paint()
        paintBlue.color = Color.BLUE
        canvas.drawRect( 0f, height - panelHeight, width.toFloat(), height.toFloat(), paintBlue )
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
        var mBackground = BitmapFactory.decodeResource(getResources(), resourceId)
        return Bitmap.createScaledBitmap(mBackground, iconWidth, iconHeight, true)
    }
}