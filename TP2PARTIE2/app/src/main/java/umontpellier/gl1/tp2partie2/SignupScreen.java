package umontpellier.gl1.tp2partie2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignupScreen extends AppCompatActivity {

    private EditText edtName, edtEmail, edtPassword, edtConfirm;
    private Button btnSignup;
    private TextView txtLogIn;


    private static final int PICK_IMAGE_REQUEST = 1;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupscreen);

        // Initialisation de DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Récupération des éléments du layout
        edtName = findViewById(R.id.edtname);
        edtEmail = findViewById(R.id.edtemail);
        edtPassword = findViewById(R.id.edtpassword);
        edtConfirm = findViewById(R.id.edtconfirm);
        btnSignup = findViewById(R.id.btnSignup);
        txtLogIn = findViewById(R.id.txtLogIn);



        // Gestion du clic sur "S'inscrire"
        btnSignup.setOnClickListener(view -> registerUser());

        // Gestion du clic sur "Se connecter"
        txtLogIn.setOnClickListener(view -> {
            startActivity(new Intent(SignupScreen.this, LoginActivity.class));
            finish();
        });
    }





    private void registerUser() {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirm.getText().toString().trim();

        // Vérification des champs
        if (TextUtils.isEmpty(name)) {
            edtName.setError("Le nom est requis !");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("L'email est requis !");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Le mot de passe est requis !");
            return;
        }

        if (password.length() < 6) {
            edtPassword.setError("Le mot de passe doit contenir au moins 6 caractères !");
            return;
        }

        if (!password.equals(confirmPassword)) {
            edtConfirm.setError("Les mots de passe ne correspondent pas !");
            return;
        }



        // Vérification si l'email existe déjà
        if (dbHelper.checkUser(email)) {
            Toast.makeText(SignupScreen.this, "Cet email est déjà utilisé !", Toast.LENGTH_LONG).show();
            return;
        }


        dbHelper.addUser(name, email, password);
        Log.d("les infos est ",name);
        Log.d("les infos est ",email);
        Log.d("les infos est ",password);
        Toast.makeText(SignupScreen.this, "Inscription réussie !", Toast.LENGTH_LONG).show();
        startActivity(new Intent(SignupScreen.this, LoginActivity.class));
        finish();
    }
}