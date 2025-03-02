package umontpellier.gl1.tp2partie2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import org.maplibre.android.MapLibre;
import org.maplibre.android.camera.CameraUpdateFactory;
import org.maplibre.android.geometry.LatLng;
import org.maplibre.android.maps.MapView;
import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.maps.OnMapReadyCallback;
import org.maplibre.android.maps.Style;
import org.maplibre.android.annotations.MarkerOptions;

public class MapActivity extends Activity implements OnMapReadyCallback {

    private MapView mapView;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private static final String TAG = "MapActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialiser MapLibre
        MapLibre.getInstance(this);

        // Définir le layout de l'activité
        setContentView(R.layout.activity_map);

        // Récupérer les coordonnées passées par l'intent
        latitude = getIntent().getDoubleExtra("latitude", 0.0);
        longitude = getIntent().getDoubleExtra("longitude", 0.0);

        Log.d(TAG, "Received coordinates: Latitude = " + latitude + ", Longitude = " + longitude);

        // Initialiser la MapView
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        // Charger la carte de manière asynchrone
        mapView.getMapAsync(this);

        // Gestion du clic sur le bouton de retour
        CardView cardCart = findViewById(R.id.cardCart);
        cardCart.setOnClickListener(v -> {
            startActivity(new Intent(MapActivity.this, MainActivity.class));
        });
    }

    @Override
    public void onMapReady(@NonNull MapLibreMap mapLibreMap) {
        Log.d(TAG, "Map is ready");

        // Token d'accès Jawg
        String accessToken = getString(R.string.jawg_access_token);
        String styleUrl = "https://api.jawg.io/styles/jawg-streets.json?access-token=" + accessToken;

        // Charger le style de la carte
        mapLibreMap.setStyle(styleUrl, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                Log.d(TAG, "Style loaded: " + style);

                // Créer un objet LatLng avec les coordonnées
                LatLng latLng = new LatLng(latitude, longitude);

                // Ajouter un marqueur à la position donnée
                mapLibreMap.addMarker(new MarkerOptions().position(latLng).title("Location"));

                // Déplacer la caméra vers la position avec un zoom
                mapLibreMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0));

                // Animer la caméra pour un effet fluide
                mapLibreMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5.0), 2000);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}