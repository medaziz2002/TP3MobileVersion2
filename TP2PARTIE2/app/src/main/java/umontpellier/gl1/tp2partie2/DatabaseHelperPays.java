package umontpellier.gl1.tp2partie2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperPays extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PaysDB.db";
    private static final int DATABASE_VERSION = 1;

    // Table et colonnes
    private static final String TABLE_PAYS = "Pays";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_IMAGE_PATH = "imagePath";

    // Commande SQL pour créer la table
    private static final String CREATE_TABLE_PAYS = "CREATE TABLE " + TABLE_PAYS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_LATITUDE + " TEXT,"
            + COLUMN_LONGITUDE + " TEXT,"
            + COLUMN_IMAGE_PATH + " TEXT"
            + ")";

    public DatabaseHelperPays(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PAYS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYS);
        onCreate(db);
    }

    // Méthode pour ajouter un pays
    public long addPays(String name, String description, String latitude, String longitude, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        values.put(COLUMN_IMAGE_PATH, imagePath);
        long id = db.insert(TABLE_PAYS, null, values);
        db.close();
        return id;
    }

    public Pays getPaysById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "Pays", // Nom de la table
                new String[]{"id", "name", "description", "latitude", "longitude", "imagePath"}, // Colonnes à récupérer
                "id = ?", // Clause WHERE
                new String[]{String.valueOf(id)}, // Arguments pour la clause WHERE
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            Pays pays = new Pays(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    cursor.getString(cursor.getColumnIndexOrThrow("latitude")),
                    cursor.getString(cursor.getColumnIndexOrThrow("longitude")),
                    cursor.getString(cursor.getColumnIndexOrThrow("imagePath"))
            );
            cursor.close();
            return pays;
        }
        return null; // Aucun pays trouvé
    }

    // Méthode pour mettre à jour un pays
    public boolean updatePays(int id, String name, String description, String latitude, String longitude, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        values.put(COLUMN_IMAGE_PATH, imagePath);

        int rowsAffected = db.update(
                TABLE_PAYS,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        return rowsAffected > 0; // Retourne true si la mise à jour a réussi
    }



    public List<Pays> getAllPays() {
        List<Pays> paysList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PAYS, null);

        if (cursor.moveToFirst()) {
            do {
                Pays pays = new Pays(

                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH))
                );
                paysList.add(pays);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return paysList;
    }




    public boolean deletePays(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(
                TABLE_PAYS, // Nom de la table
                COLUMN_ID + " = ?", // Clause WHERE
                new String[]{String.valueOf(id)} // Arguments pour la clause WHERE
        );
        db.close();
        return rowsAffected > 0; // Retourne true si la suppression a réussi
    }




}



