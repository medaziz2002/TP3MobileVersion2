package umontpellier.gl1.tp2app1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import umontpellier.gl1.tp2app1.DatabaseHelper
import umontpellier.gl1.tp2app1.MainActivity
import umontpellier.gl1.tp2app1.R

class LoginScreen : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtSignUp: TextView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginscreen)

        dbHelper = DatabaseHelper(this)

        edtEmail = findViewById(R.id.edtemail)
        edtPassword = findViewById(R.id.edtpassword)
        btnLogin = findViewById(R.id.btnSignup)
        txtSignUp = findViewById(R.id.txtSignUp)

        // Définir l'écouteur du bouton de connexion
        btnLogin.setOnClickListener { loginUser() }

        // Définir l'écouteur du texte "S'inscrire"
        txtSignUp.setOnClickListener {
            startActivity(Intent(this@LoginScreen, SignupScreen::class.java))
            finish()
        }
    }

    private fun loginUser() {
        val email = edtEmail.text.toString().trim()
        val password = edtPassword.text.toString().trim()

        // Valider les champs
        if (TextUtils.isEmpty(email)) {
            edtEmail.error = "L'email est requis !"
            return
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.error = "Le mot de passe est requis !"
            return
        }

        // Vérifier les informations de connexion
        if (dbHelper.checkUser(email, password)) {
            // Sauvegarder l'email dans SharedPreferences après une connexion réussie
            val sharedPreferences: SharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("user_email", email) // Sauvegarder l'email
            editor.apply() // Appliquer les modifications

            Toast.makeText(this@LoginScreen, "Connexion réussie !", Toast.LENGTH_LONG).show()
            startActivity(Intent(this@LoginScreen, MainActivity::class.java))
            finish() // Fermer l'activité de connexion
        } else {
            Toast.makeText(this@LoginScreen, "Email ou mot de passe incorrect !", Toast.LENGTH_LONG).show()
        }
    }
}