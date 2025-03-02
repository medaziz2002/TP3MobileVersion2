package umontpellier.gl1.tp2app1

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import umontpellier.gl1.tp2app1.DatabaseHelper
import umontpellier.gl1.tp2app1.R

class SignupScreen : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirm: EditText
    private lateinit var btnSignup: Button
    private lateinit var txtLogIn: TextView

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupscreen)

        // Initialisation de DatabaseHelper
        dbHelper = DatabaseHelper(this)

        // Récupération des éléments du layout
        edtName = findViewById(R.id.edtname)
        edtEmail = findViewById(R.id.edtemail)
        edtPassword = findViewById(R.id.edtpassword)
        edtConfirm = findViewById(R.id.edtconfirm)
        btnSignup = findViewById(R.id.btnSignup)
        txtLogIn = findViewById(R.id.txtLogIn)

        // Gestion du clic sur "S'inscrire"
        btnSignup.setOnClickListener { registerUser() }

        // Gestion du clic sur "Se connecter"
        txtLogIn.setOnClickListener {
            startActivity(Intent(this@SignupScreen, LoginScreen::class.java))
            finish()
        }
    }

    private fun registerUser() {
        val name = edtName.text.toString().trim()
        val email = edtEmail.text.toString().trim()
        val password = edtPassword.text.toString().trim()
        val confirmPassword = edtConfirm.text.toString().trim()

        // Vérification des champs
        if (TextUtils.isEmpty(name)) {
            edtName.error = "Le nom est requis !"
            return
        }

        if (TextUtils.isEmpty(email)) {
            edtEmail.error = "L'email est requis !"
            return
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.error = "Le mot de passe est requis !"
            return
        }

        if (password.length < 6) {
            edtPassword.error = "Le mot de passe doit contenir au moins 6 caractères !"
            return
        }

        if (password != confirmPassword) {
            edtConfirm.error = "Les mots de passe ne correspondent pas !"
            return
        }

        // Vérification si l'email existe déjà
        if (dbHelper.checkUser(email)) {
            Toast.makeText(this@SignupScreen, "Cet email est déjà utilisé !", Toast.LENGTH_LONG).show()
            return
        }

        // Ajout de l'utilisateur dans la base de données
        dbHelper.addUser(name, email, password)
        Log.d("les infos est ", name)
        Log.d("les infos est ", email)
        Log.d("les infos est ", password)
        Toast.makeText(this@SignupScreen, "Inscription réussie !", Toast.LENGTH_LONG).show()
        startActivity(Intent(this@SignupScreen, LoginScreen::class.java))
        finish()
    }
}