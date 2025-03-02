package umontpellier.gl1.tp2app1

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class Exercice1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex1)

        // Récupération du SensorManager
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

        val sensorNames = sensorList.map { it.name }

        // Affichage des capteurs dans une ListView
        val listView: ListView = findViewById(R.id.sensor_list)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sensorNames)
        listView.adapter = adapter


        listView.setOnItemClickListener { _, _, position, _ ->
            showSensorDetails(sensorList[position])
        }
        findViewById<CardView>(R.id.cardCart).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun showSensorDetails(sensor: Sensor) {
        AlertDialog.Builder(this)
            .setTitle(sensor.name)
            .setMessage("Type: ${sensor.type}\nFabricant: ${sensor.vendor}\nPrécision: ${sensor.resolution}")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }



}
