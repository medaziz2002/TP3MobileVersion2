package umontpellier.gl1.tp2partie2;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class HomeActivity extends AppCompatActivity {


    private DatabaseHelper dbHelperUser;

    private TextView txtName, txtEmail;

    private String currentUserEmail; // Email de l'utilisateur connecté

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Activer EdgeToEdge (équivalent de enableEdgeToEdge en Kotlin)
        setContentView(R.layout.home);

        // Associer chaque CardView à son ID et définir un écouteur de clic
        CardView cardCart = findViewById(R.id.cardCart);
        cardCart.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AjouterPays.class));
        });


        CardView cardInbox = findViewById(R.id.cardInbox);
        cardInbox.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        });


        CardView cardEx9 = findViewById(R.id.cardEx9);
        cardEx9.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MainActivityFragment.class));
        });


        CardView cardLogout = findViewById(R.id.cardLogout);
        cardLogout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });







        dbHelperUser = new DatabaseHelper(this);


        currentUserEmail = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("user_email", null);

        txtName = findViewById(R.id.txtname);
        txtEmail = findViewById(R.id.emailtxt);

        loadUserInfo();
    }




    private void loadUserInfo() {
        if (currentUserEmail != null) {

            String userName = dbHelperUser.getUserName(currentUserEmail);
            if (userName != null) {
                txtName.setText(userName);
            } else {
                txtName.setText("Nom non trouvé");
            }


            txtEmail.setText(currentUserEmail);
        } else {
            Log.d("HomeActivity", "No user email found");
        }
    }


}
