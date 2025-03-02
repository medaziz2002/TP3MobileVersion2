package umontpellier.gl1.tp2app1

import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import kotlin.math.sqrt
import android.widget.TextView

class Ex3Activity : AppCompatActivity(), SensorEventListener {

    private lateinit var rootView: View
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerationValue: TextView
    private var accelerometer: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex3)

        rootView = findViewById(R.id.root_layout)
        accelerationValue = findViewById(R.id.accelerationValue)
        sensorManager = getSystemService(SensorManager::class.java)
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        findViewById<CardView>(R.id.cardCart).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val acceleration = sqrt(
                    (it.values[0] * it.values[0] +
                            it.values[1] * it.values[1] +
                            it.values[2] * it.values[2]).toDouble()
                ).toFloat()

                // Mettre à jour la valeur de l'accélération dans le TextView
                accelerationValue.text = "Accélération: ${"%.2f".format(acceleration)}"

                rootView.setBackgroundColor(
                    when {
                        acceleration < 5 -> Color.GREEN
                        acceleration in 5.0..15.0 -> Color.BLACK
                        else -> Color.RED
                    }
                )
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Non utilisé
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }
}