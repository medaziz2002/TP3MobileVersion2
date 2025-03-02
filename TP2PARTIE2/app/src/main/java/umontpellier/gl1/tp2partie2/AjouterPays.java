package umontpellier.gl1.tp2partie2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AjouterPays extends AppCompatActivity {

    private boolean isEditMode = false;
    private int paysId = -1;




    private EditText inputName, inputDescription, inputLatitude, inputLongitude;
    private TextView txtname;
    private Button btnValider, btnSelectFile;
    private ImageView imgViewSelectedImage, iconEdit;

    private Uri imageUri;

    private DatabaseHelperPays dbHelper;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final String TAG = "AjouterPays";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter_pays);


        inputName = findViewById(R.id.input_name);
        inputDescription = findViewById(R.id.input_description);
        inputLatitude = findViewById(R.id.input_latitude);
        inputLongitude = findViewById(R.id.input_longitude);
        btnValider = findViewById(R.id.btn_valider);
        btnSelectFile = findViewById(R.id.btn_select_file);
        imgViewSelectedImage = findViewById(R.id.imgView_selected_image);
        iconEdit = findViewById(R.id.icon_edit); // Initialisation de l'icône d'édition

        // Initialisation de SQLite
        dbHelper = new DatabaseHelperPays(this);

        // Vérifier si on est en mode modification
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("PAYS_ID")) {
            isEditMode = true;
            paysId = extras.getInt("PAYS_ID");

            // Charger les informations du pays
            Pays pays = dbHelper.getPaysById(paysId);
            if (pays != null) {
                Log.d(TAG, "Pays récupéré : " + pays.getName());
                inputName.setText(pays.getName());
                inputDescription.setText(pays.getDescription());
                inputLatitude.setText(pays.getLatitude());
                inputLongitude.setText(pays.getLongitude());

                // Charger l'image si elle existe
                if (pays.getImagePath() != null) {
                    Log.d(TAG, "Chemin de l'image : " + pays.getImagePath());
                    File imgFile = new File(pays.getImagePath());
                    if (imgFile.exists()) {
                        imageUri = Uri.fromFile(imgFile);
                        imgViewSelectedImage.setImageURI(imageUri);
                        imgViewSelectedImage.setVisibility(View.VISIBLE);
                    } else {
                        Log.d(TAG, "Le fichier image n'existe pas : " + pays.getImagePath());
                    }
                } else {
                    Log.d(TAG, "Aucun chemin d'image trouvé pour le pays");
                }
            } else {
                Log.d(TAG, "Aucun pays trouvé avec l'ID : " + paysId);
            }
        }





        // Gestion de la visibilité des boutons
        if (isEditMode) {
            btnSelectFile.setVisibility(View.GONE);
            txtname=findViewById(R.id.txtname);
            txtname.setText("Modifier un Pays");
            iconEdit.setVisibility(View.VISIBLE); // Afficher l'icône d'édition en mode édition
        } else {
            btnSelectFile.setVisibility(View.VISIBLE);
            iconEdit.setVisibility(View.GONE); // Masquer l'icône d'édition en mode ajout
        }

        // Bouton pour sélectionner une image
        btnSelectFile.setOnClickListener(view -> openGallery());

        // Gestion du clic sur l'icône d'édition
        iconEdit.setOnClickListener(view -> openGallery());

        // Bouton pour enregistrer les données
        btnValider.setOnClickListener(view -> {
            if (isEditMode) {
                updateData(); // Mode modification
            } else {
                uploadData(); // Mode ajout
            }
        });

        // Bouton pour retourner à l'écran d'accueil
        CardView cardView = findViewById(R.id.cardCart);
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(AjouterPays.this, HomeActivity.class);
            startActivity(intent);
        });


    }

    private void updateData() {
        String name = inputName.getText().toString().trim();
        String description = inputDescription.getText().toString().trim();
        String latitude = inputLatitude.getText().toString().trim();
        String longitude = inputLongitude.getText().toString().trim();

        // Vérifiez que tous les champs sont remplis
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)) {
            Log.d(TAG, "Un ou plusieurs champs sont vides");
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérifiez qu'une image a été sélectionnée
        if (imageUri == null) {
            Log.d(TAG, "Aucune image sélectionnée");
            Toast.makeText(this, "Veuillez sélectionner une image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Enregistrer l'image dans le stockage interne
        String imagePath = saveImageToInternalStorage(imageUri);
        if (imagePath == null) {
            Log.d(TAG, "Erreur lors de l'enregistrement de l'image");
            Toast.makeText(this, "Erreur lors de l'enregistrement de l'image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mettre à jour les données dans SQLite
        boolean isUpdated = dbHelper.updatePays(paysId, name, description, latitude, longitude, imagePath);
        if (isUpdated) {
            Log.d(TAG, "Pays mis à jour avec succès");
            Toast.makeText(this, "Pays mis à jour avec succès", Toast.LENGTH_SHORT).show();
            finish(); // Fermer l'activité après la mise à jour
        } else {
            Log.d(TAG, "Échec de la mise à jour du pays");
            Toast.makeText(this, "Échec de la mise à jour du pays", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Sélectionner une image"), IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Redimensionner l'image avant de l'afficher
            int targetWidth = imgViewSelectedImage.getWidth();
            int targetHeight = imgViewSelectedImage.getHeight();

            // Si les dimensions ne sont pas encore disponibles, utilisez des valeurs par défaut
            if (targetWidth <= 0 || targetHeight <= 0) {
                targetWidth = 500; // Largeur par défaut
                targetHeight = 500; // Hauteur par défaut
            }

            Bitmap resizedBitmap = resizeBitmap(imageUri, targetWidth, targetHeight);
            if (resizedBitmap != null) {
                imgViewSelectedImage.setImageBitmap(resizedBitmap);
                imgViewSelectedImage.setVisibility(View.VISIBLE);
                if (isEditMode) {
                    iconEdit.setVisibility(View.VISIBLE); // Afficher l'icône d'édition en mode édition
                }
            } else {
                Toast.makeText(this, "Erreur lors du chargement de l'image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadData() {
        String name = inputName.getText().toString().trim();
        String description = inputDescription.getText().toString().trim();
        String latitude = inputLatitude.getText().toString().trim();
        String longitude = inputLongitude.getText().toString().trim();

        // Vérifiez que tous les champs sont remplis
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)) {
            Log.d(TAG, "Un ou plusieurs champs sont vides");
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérifiez qu'une image a été sélectionnée
        if (imageUri == null) {
            Log.d(TAG, "Aucune image sélectionnée");
            Toast.makeText(this, "Veuillez sélectionner une image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Enregistrer l'image dans le stockage interne
        String imagePath = saveImageToInternalStorage(imageUri);
        if (imagePath == null) {
            Log.d(TAG, "Erreur lors de l'enregistrement de l'image");
            Toast.makeText(this, "Erreur lors de l'enregistrement de l'image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ajouter les données dans SQLite
        long id = dbHelper.addPays(name, description, latitude, longitude, imagePath);
        if (id != -1) {
            Log.d(TAG, "Pays ajouté avec succès");
            Toast.makeText(this, "Pays ajouté avec succès", Toast.LENGTH_SHORT).show();
            finish(); // Fermer l'activité après l'ajout
        } else {
            Log.d(TAG, "Échec de l'ajout du pays");
            Toast.makeText(this, "Échec de l'ajout du pays", Toast.LENGTH_SHORT).show();
        }
    }

    private String saveImageToInternalStorage(Uri uri) {
        try {
            // Redimensionner l'image avant de l'enregistrer
            int targetWidth = 800; // Largeur maximale
            int targetHeight = 800; // Hauteur maximale
            Bitmap resizedBitmap = resizeBitmap(uri, targetWidth, targetHeight);

            if (resizedBitmap == null) {
                return null;
            }

            // Enregistrer l'image redimensionnée
            File file = new File(getFilesDir(), System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream); // Compression à 80%
            outputStream.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors de l'enregistrement de l'image : " + e.getMessage());
            return null;
        }
    }

    private Bitmap resizeBitmap(Uri uri, int targetWidth, int targetHeight) {
        try {
            // Charger les dimensions de l'image
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true; // Ne pas charger l'image en mémoire
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();

            // Calculer le ratio de redimensionnement
            int imageWidth = options.outWidth;
            int imageHeight = options.outHeight;
            int scaleFactor = Math.min(imageWidth / targetWidth, imageHeight / targetHeight);

            // Charger l'image redimensionnée
            options.inJustDecodeBounds = false;
            options.inSampleSize = scaleFactor;
            inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();

            return bitmap;
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors du redimensionnement de l'image : " + e.getMessage());
            return null;
        }
    }




}