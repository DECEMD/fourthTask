package com.example.testone

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.icu.util.Calendar
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import java.time.LocalTime as Vremya

class ClockTime @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paintCircle = Paint()
    private val paintPointer = Paint()
    private var radius = 0.0f
    private var shades : Int = 0
    private var clockColor: Int = 0
    private var circleColor: Int = 0
    private var hourColor: Int = 0
    private var minuteColor: Int = 0
    private var secondColor: Int = 0
    private var hourThickness: Float = 0.0f
    private var minuteThickness: Float = 0.0f
    private var secondThickness: Float = 0.0f
    private var circleThickness: Float = 0.0f

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClockTime)
        clockColor = typedArray.getColor(R.styleable.ClockTime_clockColor, Color.YELLOW)
        circleColor = typedArray.getColor(R.styleable.ClockTime_circleColor, Color.RED)
        hourColor = typedArray.getColor(R.styleable.ClockTime_hourColor, Color.BLACK)
        minuteColor= typedArray.getColor(R.styleable.ClockTime_minuteColor, Color.BLUE)
        secondColor = typedArray.getColor(R.styleable.ClockTime_secondColor, Color.GREEN)
        hourThickness = typedArray.getDimension(R.styleable.ClockTime_hourThickness, 20f)
        minuteThickness = typedArray.getDimension(R.styleable.ClockTime_minuteThickness, 15f)
        circleThickness = typedArray.getDimension(R.styleable.ClockTime_circleThickness, 30f)
        secondThickness = typedArray.getDimension(R.styleable.ClockTime_secondThickness, 10f)

        typedArray.recycle()
    }

    override fun onSizeChanged(width: Int,
                               height: Int,
                               oldWidth: Int,
                               oldHeight: Int) {
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paintCircle.style = Paint.Style.FILL
        paintCircle.color = clockColor
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            radius,
            paintCircle
        )
        paintCircle.style = Paint.Style.STROKE
        paintCircle.color = circleColor
        paintCircle.strokeWidth = circleThickness
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            radius,
            paintCircle
        )
        for (i in 1..12) {
            canvas.drawLine(
                (width / 2).toFloat(),
                (height / 2).toFloat() - radius + 70f,
                (width / 2).toFloat(),
                (height / 2) - radius,
                paintCircle
            )
            canvas.rotate(
                (360 / 12).toFloat(),
                (width / 2).toFloat(),
                (height / 2).toFloat()
            )

        }

        for (i in 1..60) {
            canvas.drawLine(
                (width / 2).toFloat(),
                (height / 2).toFloat() - radius + 30f,
                (width / 2).toFloat(),
                (height / 2).toFloat() - radius + 20f,
                paintCircle
            )
            canvas.rotate(
                (360 / 60).toFloat(),
                (width / 2).toFloat(),
                (height / 2).toFloat()
            )

        }

        val clock = Vremya.now()
        val hours = clock.getHour()
        val minutes = clock.getMinute()
        val seconds = clock.getSecond()

        paintPointer.strokeWidth = hourThickness
        paintPointer.color = hourColor
        val hourRotation = (360 / 12).toFloat() * (30 + hours)
        canvas.rotate(
            hourRotation,
            (width / 2).toFloat(),
            (height / 2).toFloat()
        )
        canvas.drawLine(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width / 2).toFloat(),
            (height / 2).toFloat() + 250f,
            paintPointer
        )
        paintPointer.strokeWidth = minuteThickness
        paintPointer.color = minuteColor
        val minutesRotation = (360 / 60).toFloat() * (30 + minutes) - hourRotation
        canvas.rotate(
            minutesRotation,
            (width / 2).toFloat(),
            (height / 2).toFloat()
        )
        canvas.drawLine(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width / 2).toFloat(),
            (height / 2).toFloat() + 300f,
            paintPointer
        )
        paintPointer.strokeWidth = secondThickness
        paintPointer.color = secondColor
        canvas.rotate(
            (360 / 60).toFloat() * (30 + seconds) - minutesRotation - hourRotation,
            (width / 2).toFloat(), (height / 2).toFloat()
        )
        canvas.drawLine(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width / 2).toFloat(),
            (height / 2).toFloat() + 300f,
            paintPointer
        )

        invalidate()
    }

}