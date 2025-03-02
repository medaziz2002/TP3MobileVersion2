package umontpellier.gl1.tp2app1


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.cardview.widget.CardView
import org.maplibre.android.MapLibre
import org.maplibre.android.annotations.MarkerOptions
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.OnMapReadyCallback

class MapActivity : Activity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private val TAG = "MapActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        MapLibre.getInstance(this)


        setContentView(R.layout.activity_map)


        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)

        Log.d(TAG, "Received coordinates: Latitude = $latitude, Longitude = $longitude")


        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)


        mapView.getMapAsync(this)

        findViewById<CardView>(R.id.cardCart).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onMapReady(mapLibreMap: MapLibreMap) {
        Log.d(TAG, "Map is ready")

        val accessToken = getString(R.string.jawg_access_token) // Token d'accès Jawg
        val styleUrl = "https://api.jawg.io/styles/jawg-streets.json?access-token=$accessToken"

        mapLibreMap.setStyle(styleUrl) { style ->
            Log.d(TAG, "Style loaded: $style")

            val latLng = LatLng(latitude, longitude)

            mapLibreMap.addMarker(MarkerOptions().position(latLng).title("Location"))

            mapLibreMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0))
            mapLibreMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5.0), 2000)
        }
    }


    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
