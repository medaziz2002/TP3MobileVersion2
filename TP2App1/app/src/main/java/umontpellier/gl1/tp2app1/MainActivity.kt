package umontpellier.gl1.tp2app1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private var currentUserEmail: String? = null // Email de l'utilisateur connecté
    private lateinit var txtName: TextView
    private lateinit var txtEmail: TextView
    private lateinit var dbHelperUser: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialisation de DatabaseHelper
        dbHelperUser = DatabaseHelper(this)

        // Récupération de l'email de l'utilisateur connecté depuis SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        currentUserEmail = sharedPreferences.getString("user_email", null)

        // Initialisation des vues
        txtName = findViewById(R.id.txtname)
        txtEmail = findViewById(R.id.emailtxt)

        // Charger les informations de l'utilisateur
        loadUserInfo()

        // Associer chaque CardView à son ID et définir un écouteur de clic
        findViewById<CardView>(R.id.cardCart).setOnClickListener {
            startActivity(Intent(this, Exercice1::class.java))
        }

        findViewById<CardView>(R.id.cardMap).setOnClickListener {
            startActivity(Intent(this, Ex2Activity::class.java))
        }

        findViewById<CardView>(R.id.cardInbox).setOnClickListener {
            startActivity(Intent(this, Ex3Activity::class.java))
        }

        findViewById<CardView>(R.id.cardSetting).setOnClickListener {
            startActivity(Intent(this, Ex4Activity::class.java))
        }

        findViewById<CardView>(R.id.cardFavourite).setOnClickListener {
            startActivity(Intent(this, Ex5Activity::class.java))
        }

        findViewById<CardView>(R.id.cardLogout).setOnClickListener {
            startActivity(Intent(this, Ex6Activity::class.java))
        }

        findViewById<CardView>(R.id.cardNew1).setOnClickListener {
            startActivity(Intent(this, Ex7Activity::class.java))
        }

        findViewById<CardView>(R.id.cardNew2).setOnClickListener {
            startActivity(Intent(this, LoginScreen::class.java))
        }


    }

    private fun loadUserInfo() {
        if (currentUserEmail != null) {
            val userName = dbHelperUser.getUserName(currentUserEmail!!)
            if (userName != null) {
                txtName.text = userName
            } else {
                txtName.text = "Nom non trouvé"
            }
            txtEmail.text = currentUserEmail
        } else {
            Log.d("MainActivity", "No user email found")
        }
    }
}