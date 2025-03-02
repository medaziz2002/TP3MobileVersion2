package umontpellier.gl1.tp2partie2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.io.File;

public class DetailsPaysFragment extends Fragment {

    private ImageView imagePays;
    private TextView textName, textDescription, textCoordinates;
    private Button btnShowMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_pays_fragement, container, false);

        imagePays = view.findViewById(R.id.image_pays);
        textName = view.findViewById(R.id.text_name);
        textDescription = view.findViewById(R.id.text_description);
        textCoordinates = view.findViewById(R.id.text_coordinates);
        btnShowMap = view.findViewById(R.id.btn_show_map);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String nomPays = bundle.getString("NAME");
            String description = bundle.getString("DESCRIPTION");
            String latitude = bundle.getString("LATITUDE");
            String longitude = bundle.getString("LONGITUDE");
            String imagePath = bundle.getString("IMAGE_PATH");

            textName.setText(nomPays);
            textDescription.setText(description);
            textCoordinates.setText(latitude + ", " + longitude);

            if (imagePath != null) {
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    imagePays.setImageURI(Uri.fromFile(imgFile));
                }
            }

            btnShowMap.setOnClickListener(v -> {
                Intent mapIntent = new Intent(getActivity(), MapActivity.class);
                mapIntent.putExtra("latitude", Double.parseDouble(latitude));
                mapIntent.putExtra("longitude", Double.parseDouble(longitude));
                startActivity(mapIntent);
            });
        }

        return view;
    }
}