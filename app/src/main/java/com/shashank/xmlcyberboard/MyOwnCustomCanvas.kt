package com.shashank.xmlcyberboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MyOwnCustomCanvas @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }
    private var isEraserMode = false

    private var path = Path()
    private val pathList = mutableListOf<Pair<Path, Paint>>()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for ((p, paint) in pathList) {
            canvas.drawPath(p, paint)
        }
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                path = Path()
                path.moveTo(event.x, event.y)
                if (!isEraserMode) {
                    pathList.add(Pair(path, Paint(paint)))
                }
                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                path.lineTo(event.x, event.y)
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                path.lineTo(event.x, event.y)
                if (isEraserMode) {
                    erasePath(event.x, event.y)
                } else {
                    pathList.add(Pair(path, Paint(paint)))
                }
                path = Path()
                invalidate()
            }
        }
        return true
    }

    fun setEraseMode(enable: Boolean) {
        isEraserMode = enable
        paint.strokeWidth = if (isEraserMode) 50f else 10f
    }

    private fun erasePath(x: Float, y: Float) {
        val iterator = pathList.iterator()
        while (iterator.hasNext()) {
            val (p) = iterator.next()
            val rectF = RectF()
            p.computeBounds(rectF, true)
            if (rectF.contains(x, y)) {
                iterator.remove()
            }
        }
    }

    fun clearCanvas() {
        pathList.clear()
        path.reset()
        invalidate()
    }
}