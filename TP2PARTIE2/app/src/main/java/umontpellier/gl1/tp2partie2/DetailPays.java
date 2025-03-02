package umontpellier.gl1.tp2partie2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.io.File;

public class DetailPays extends AppCompatActivity {

    private TextView textName, textDescription, textCoordinates;
    private ImageView imagePays;
    private Button btnShowMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pays);

        // Initialiser les vues
        textName = findViewById(R.id.text_name);
        textDescription = findViewById(R.id.text_description);
        textCoordinates = findViewById(R.id.text_coordinates);
        imagePays = findViewById(R.id.image_pays);
        btnShowMap = findViewById(R.id.btn_show_map);

        // Récupérer les données du pays depuis l'Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        String description = intent.getStringExtra("DESCRIPTION");
        String latitude = intent.getStringExtra("LATITUDE");
        String longitude = intent.getStringExtra("LONGITUDE");
        String imagePath = intent.getStringExtra("IMAGE_PATH");

        // Afficher les données du pays
        textName.setText(name);
        textDescription.setText(description);
        textCoordinates.setText(latitude + ", " + longitude);

        // Charger l'image du pays
        if (imagePath != null) {
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                imagePays.setImageURI(Uri.fromFile(imgFile));
            }
        }

        // Bouton pour afficher la carte
        btnShowMap.setOnClickListener(v -> {
            Intent mapIntent = new Intent(DetailPays.this, MapActivity.class);
            mapIntent.putExtra("latitude", Double.parseDouble(latitude));
            mapIntent.putExtra("longitude", Double.parseDouble(longitude));
            startActivity(mapIntent);
        });
        CardView cardView = findViewById(R.id.cardCart);
        cardView.setOnClickListener(v -> {
            Intent t = new Intent(DetailPays.this, HomeActivity.class);
            startActivity(t);
        });
    }

}