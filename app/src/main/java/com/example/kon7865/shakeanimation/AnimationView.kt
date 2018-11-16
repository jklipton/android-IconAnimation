package com.example.kon7865.shakeanimation

import android.content.Context
import android.graphics.*
import android.view.View
import android.view.MotionEvent
import java.util.*
import kotlin.concurrent.timerTask


class AnimationView(context: Context) : View(context) {

    //Globals
    //TODO: set floats to be responsive for screen widths
    lateinit var heartIcon : Icon
    lateinit var toiletIcon : Icon
    lateinit var foodIcon : Icon
    lateinit var medsIcon : Icon
    lateinit var moneyIcon : Icon
    lateinit var socialIcon : Icon
    lateinit var createIcon : Icon
    lateinit var settingsIcon : Icon

    val iconList = ArrayList<Icon>()
    var iconsItitialized = false

    //CANVAS VARIABLES
    var canvasWidth = 0
    var canvasHeight = 0
    var panelHeight = 0.0f
    var iconMargin = 0.0f
    var iconSize = 0

    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)
        this.setOnTouchListener { v, event -> handleMotionEvent(event)}
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        canvasWidth = right
        canvasHeight = bottom

        setGlobalMeasurements()
        initializeIcons()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //draw each layer in a function, go from furthest back to front
        drawBackground(canvas)
        drawPanels(canvas)

        for (a: Icon in iconList) {
            a.animate()
            a.onDraw(canvas)
        }

        postInvalidateDelayed(1000 / 30)
    }

    private fun initializeIcons() {
        var topRowY = 5f
        var bottomRowY = canvasHeight - panelHeight + 10
        var columnOneX = iconMargin
        var columnTwoX = (iconMargin * 2 + iconSize)
        var columnThreeX = (iconMargin * 3 + iconSize * 2)
        var columnFourX = (iconMargin * 4 + iconSize * 3)

        //TODO: refactor with loop and arrays n shit
        if (!iconsItitialized) {
            heartIcon = Icon(getBitmapFromResources(R.drawable.icon_heart), columnOneX, topRowY)
            toiletIcon = Icon(getBitmapFromResources(R.drawable.icon_toilet), columnTwoX, topRowY)
            foodIcon = Icon(getBitmapFromResources(R.drawable.icon_food), columnThreeX, topRowY)
            medsIcon = Icon(getBitmapFromResources(R.drawable.icon_meds), columnFourX, topRowY)
            moneyIcon = Icon(getBitmapFromResources(R.drawable.icon_money), columnOneX, bottomRowY)
            socialIcon = Icon(getBitmapFromResources(R.drawable.icon_social), columnTwoX, bottomRowY)
            createIcon = Icon(getBitmapFromResources(R.drawable.icon_create), columnThreeX, bottomRowY)
            settingsIcon = Icon(getBitmapFromResources(R.drawable.icon_settings), columnFourX, bottomRowY)

            iconList.addAll(
                Arrays.asList(
                    heartIcon,
                    toiletIcon,
                    foodIcon,
                    medsIcon,
                    moneyIcon,
                    socialIcon,
                    createIcon,
                    settingsIcon
                )
            )

            iconsItitialized = true
        }
    }

    private fun setGlobalMeasurements() {
        panelHeight = (canvasHeight / 7).toFloat()
        if (panelHeight < 150f) panelHeight = 150f
        iconSize = panelHeight.toInt() - 10
        iconMargin = ((canvasWidth - (iconSize * 4)) / 5).toFloat()
    }

    private fun handleMotionEvent( motionEvent: MotionEvent ):Boolean {
        //loop through array of icons, check if x and y in 100px for each icon1
        for (a: Icon in iconList) {
            var willAnimate = (Math.abs(motionEvent.x - a.xp - 50) < 100 && Math.abs(motionEvent.y - a.yp - 50) < 100)
            if (willAnimate) {
                a.runAnimation = true
                Timer().schedule(timerTask { a.runAnimation = false }, 500)
            }
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

    //index name (R.drawable.name) is a standin for resource ID int, gets compiled at build time
    fun getBitmapFromResources(resourceId: Int) : Bitmap {
        //TODO: switch to svg
        var mBackground = BitmapFactory.decodeResource(getResources(), resourceId)
        return  Bitmap.createScaledBitmap(mBackground, iconSize, iconSize, true)
    }
}