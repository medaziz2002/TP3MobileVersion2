package umontpellier.gl1.tp2app1
import android.app.AlertDialog
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class Ex2Activity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorList: List<Sensor>
    private lateinit var sensorNames: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex2)

        // Initialisation des composants
        val listView: ListView = findViewById(R.id.sensor_list)

        // Récupération du SensorManager et de la liste des capteurs
        sensorManager = getSystemService(SensorManager::class.java)
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

        // Extraction des noms des capteurs
        sensorNames = sensorList.map { it.name }.toMutableList()

        // Configuration de l'adaptateur pour la ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sensorNames)
        listView.adapter = adapter
        findViewById<CardView>(R.id.cardCart).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Gestion du clic sur un élément de la liste
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val sensorName = sensorNames[position]
            val isAvailable = isSensorAvailable(sensorName)
            showSensorStatus(sensorName, isAvailable)
        }
    }

    private fun isSensorAvailable(sensorName: String): Boolean {
        return sensorList.any { it.name == sensorName }
    }

    private fun showSensorStatus(sensorName: String, isAvailable: Boolean) {
        AlertDialog.Builder(this)
            .setTitle(sensorName)
            .setMessage(if (isAvailable) "Capteur disponible." else "Capteur non disponible.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}