package umontpellier.gl1.tp2partie2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView txtSignUp;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screan);

        dbHelper = new DatabaseHelper(this);

        edtEmail = findViewById(R.id.edtemail);
        edtPassword = findViewById(R.id.edtpassword);
        btnLogin = findViewById(R.id.btnSignup);
        txtSignUp = findViewById(R.id.txtSignUp);

        // Définir l'écouteur du bouton de connexion
        btnLogin.setOnClickListener(view -> loginUser());

        // Définir l'écouteur du texte "S'inscrire"
        txtSignUp.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignupScreen.class));
            finish();
        });
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Valider les champs
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("L'email est requis !");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Le mot de passe est requis !");
            return;
        }

        // Vérifier les informations de connexion
        if (dbHelper.checkUser(email, password)) {
            // Sauvegarder l'email dans SharedPreferences après une connexion réussie
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user_email", email); // Sauvegarder l'email
            editor.apply(); // Appliquer les modifications

            Toast.makeText(LoginActivity.this, "Connexion réussie !", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish(); // Fermer l'activité de connexion
        } else {
            Toast.makeText(LoginActivity.this, "Email ou mot de passe incorrect !", Toast.LENGTH_LONG).show();
        }
    }
}