package umontpellier.gl1.tp2partie2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class MainActivityFragment extends AppCompatActivity {

    private DatabaseHelperPays dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        dbHelper = new DatabaseHelperPays(this);
        List<Pays> paysList = dbHelper.getAllPays();

        // Charger la liste des pays dans le conteneur de liste
        loadPaysListFragment();

        // Gestion du clic sur la CardView (si nécessaire)
        CardView cardView = findViewById(R.id.cardCart);
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityFragment.this, HomeActivity.class);
            startActivity(intent);
        });
    }

    public void loadPaysListFragment() {
        PaysListFragment paysListFragment = new PaysListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, paysListFragment); // Utiliser le bon ID
        fragmentTransaction.commit();
    }
}