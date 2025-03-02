package umontpellier.gl1.tp2app1

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class Ex4Activity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var compassView: CompassView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex4)

        compassView = CompassView(this)

        // Ajout dynamique de la CompassView au FrameLayout
        val compassContainer = findViewById<FrameLayout>(R.id.compass_container)
        compassContainer.addView(compassView)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!

        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        }

        findViewById<CardView>(R.id.cardCart).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            compassView.updateDirection(x, y)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Non utilisé
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }

    private inner class CompassView(context: Context) : View(context) {
        private val paint: Paint = Paint()
        private var x = 0f
        private var y = 0f

        init {
            paint.color = Color.RED
            paint.strokeWidth = 10f
            paint.textSize = 50f
            paint.textAlign = Paint.Align.CENTER
        }

        fun updateDirection(x: Float, y: Float) {
            this.x = x
            this.y = y
            invalidate()
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            val width = width
            val height = height
            val centerX = width / 2
            val centerY = height / 2

            canvas.drawColor(Color.WHITE)
            paint.color = Color.BLACK
            canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), 200f, paint)

            paint.color = Color.RED
            if (Math.abs(x) > Math.abs(y)) {
                if (x < -2) {
                    canvas.drawText("→ Droite", centerX.toFloat(), centerY.toFloat(), paint)
                } else if (x > 2) {
                    canvas.drawText("← Gauche", centerX.toFloat(), centerY.toFloat(), paint)
                }
            } else {
                if (y < -2) {
                    canvas.drawText("↑ Haut", centerX.toFloat(), centerY.toFloat(), paint)
                } else if (y > 2) {
                    canvas.drawText("↓ Bas", centerX.toFloat(), centerY.toFloat(), paint)
                }
            }
        }
    }
}
