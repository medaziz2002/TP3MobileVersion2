package umontpellier.gl1.tp2app1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserManager.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USER = "user"
        private const val COLUMN_USER_ID = "user_id"
        private const val COLUMN_USER_NAME = "user_name"
        private const val COLUMN_USER_EMAIL = "user_email"
        private const val COLUMN_USER_PASSWORD = "user_password"

        // Commande SQL pour créer la table
        private const val CREATE_TABLE_USER = ("CREATE TABLE $TABLE_USER("
                + "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_USER_NAME TEXT,"
                + "$COLUMN_USER_EMAIL TEXT,"
                + "$COLUMN_USER_PASSWORD TEXT)")
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    // Méthode pour ajouter un utilisateur
    fun addUser(name: String, email: String, password: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_NAME, name)
            put(COLUMN_USER_EMAIL, email)
            put(COLUMN_USER_PASSWORD, password)
        }
        db.insert(TABLE_USER, null, values)
        db.close()
    }

    // Méthode pour vérifier si l'email existe déjà
    fun checkUser(email: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_USER, arrayOf(COLUMN_USER_ID), "$COLUMN_USER_EMAIL=?", arrayOf(email),
            null, null, null
        )
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        return cursorCount > 0
    }

    // Méthode pour vérifier l'email et le mot de passe
    fun checkUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_USER, arrayOf(COLUMN_USER_ID),
            "$COLUMN_USER_EMAIL=? AND $COLUMN_USER_PASSWORD=?", arrayOf(email, password),
            null, null, null
        )
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        return cursorCount > 0
    }

    // Méthode pour récupérer le nom de l'utilisateur
    fun getUserName(email: String): String? {
        val db = this.readableDatabase
        val cursor: Cursor? = db.query(
            TABLE_USER, arrayOf(COLUMN_USER_NAME), "$COLUMN_USER_EMAIL=?", arrayOf(email),
            null, null, null
        )
        return cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex(COLUMN_USER_NAME)
                if (columnIndex >= 0) { // Vérifiez que la colonne existe
                    it.getString(columnIndex)
                } else {
                    null
                }
            } else {
                null
            }
        }.also {
            db.close()
        }
    }
}