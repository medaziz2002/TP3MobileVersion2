package umontpellier.gl1.tp2partie2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PaysAdapter paysAdapter;
    private DatabaseHelperPays dbHelper;

    private DatabaseHelper dbHelperUser;
    private ImageView profileImageView;

    private String currentUserEmail; // Email de l'utilisateur connecté

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelperPays(this);
        dbHelperUser = new DatabaseHelper(this);


        currentUserEmail = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("user_email", null);



        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        List<Pays> paysList = dbHelper.getAllPays();


        paysAdapter = new PaysAdapter(this, paysList,dbHelper,false);
        recyclerView.setAdapter(paysAdapter);


        CardView cardView = findViewById(R.id.cardCart);
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Rafraîchir la liste des pays à chaque retour sur l'écran
        List<Pays> paysList = dbHelper.getAllPays();
        paysAdapter = new PaysAdapter(this, paysList,dbHelper,false);
        recyclerView.setAdapter(paysAdapter);
    }




}