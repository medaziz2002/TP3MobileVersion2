package umontpellier.gl1.tp2app1

import android.app.Application
import org.maplibre.android.MapLibre
import org.maplibre.android.WellKnownTileServer

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize MapLibre
        MapLibre.getInstance(this, getString(R.string.jawg_access_token), WellKnownTileServer.MapLibre)
    }
}