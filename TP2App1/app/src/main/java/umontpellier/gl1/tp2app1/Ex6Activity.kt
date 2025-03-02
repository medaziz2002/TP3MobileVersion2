package umontpellier.gl1.tp2app1


import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class Ex6Activity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var proximitySensor: Sensor? = null
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex6)

        // Initialisation des composants
        imageView = findViewById(R.id.proximity_image)
        sensorManager = getSystemService(SensorManager::class.java)
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        // Enregistrement du listener pour le capteur de proximité
        proximitySensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        findViewById<CardView>(R.id.cardCart).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_PROXIMITY) {
                // Vérifier si un objet est proche ou loin
                if (it.values[0] < proximitySensor?.maximumRange ?: 0f) {
                    imageView.setImageResource(R.drawable.near_image) // Objet proche
                } else {
                    imageView.setImageResource(R.drawable.far_image) // Objet loin
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Non utilisé
    }

    override fun onDestroy() {
        super.onDestroy()
        // Désenregistrer le listener pour éviter les fuites de mémoire
        sensorManager.unregisterListener(this)
    }
}