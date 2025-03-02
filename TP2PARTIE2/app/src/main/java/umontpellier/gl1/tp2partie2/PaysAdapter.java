package umontpellier.gl1.tp2partie2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class PaysAdapter extends RecyclerView.Adapter<PaysAdapter.PaysViewHolder> {

    private Context context;
    private List<Pays> paysList;

    private boolean useFragments;


    private DatabaseHelperPays dbHelper;

    public PaysAdapter(Context context, List<Pays> paysList, DatabaseHelperPays dbHelper, boolean useFragments) {
        this.context = context;
        this.paysList = paysList;
        this.dbHelper = dbHelper;
        this.useFragments = useFragments; // Initialiser l'indicateur
    }

    @NonNull
    @Override
    public PaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pays, parent, false);
        return new PaysViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PaysViewHolder holder, int position) {
        Pays pays = paysList.get(position);


        holder.textName.setText(pays.getName());
        holder.textDescription.setText(pays.getDescription());
        holder.textCoordinates.setText(pays.getLatitude() + ", " + pays.getLongitude());


        File imgFile = new File(pays.getImagePath());
        if (imgFile.exists()) {
            holder.imagePays.setImageURI(Uri.fromFile(imgFile));
        }


        holder.iconEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AjouterPays.class);
            intent.putExtra("PAYS_ID", pays.getId());
            context.startActivity(intent);
        });


        holder.iconDelete.setOnClickListener(v -> {
            boolean isDeleted = dbHelper.deletePays(pays.getId()); // Utilisez dbHelper ici
            if (isDeleted) {
                paysList.remove(position); // Supprimer l'élément de la liste
                notifyItemRemoved(position); // Notifier l'adaptateur
                Toast.makeText(context, "Pays supprimé", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Échec de la suppression", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnShowMap.setOnClickListener(v -> {
            Intent intent = new Intent(context, MapActivity.class);
            intent.putExtra("latitude", Double.parseDouble(pays.getLatitude()));
            intent.putExtra("longitude", Double.parseDouble(pays.getLongitude()));
            context.startActivity(intent);
        });




        holder.icon_view_details.setOnClickListener(v -> {
            if (useFragments && context instanceof FragmentActivity) {
                FragmentActivity fragmentActivity = (FragmentActivity) context;
                DetailsPaysFragment detailsPaysFragment = new DetailsPaysFragment();

                Bundle bundle = new Bundle();
                bundle.putString("NAME", pays.getName());
                bundle.putString("DESCRIPTION", pays.getDescription());
                bundle.putString("LATITUDE", pays.getLatitude());
                bundle.putString("LONGITUDE", pays.getLongitude());
                bundle.putString("IMAGE_PATH", pays.getImagePath());
                detailsPaysFragment.setArguments(bundle);

                FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, detailsPaysFragment); // Utiliser le bon ID
                fragmentTransaction.addToBackStack(null); // Permet de revenir à la liste
                fragmentTransaction.commit();
            } else {
                // Logique pour les activités
                Intent intent = new Intent(context, DetailPays.class);
                intent.putExtra("NAME", pays.getName());
                intent.putExtra("DESCRIPTION", pays.getDescription());
                intent.putExtra("LATITUDE", pays.getLatitude());
                intent.putExtra("LONGITUDE", pays.getLongitude());
                intent.putExtra("IMAGE_PATH", pays.getImagePath());
                context.startActivity(intent);
            }
        });

    }





    @Override
    public int getItemCount() {
        return paysList.size();
    }

    public static class PaysViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textDescription, textCoordinates;
        ImageView imagePays, iconEdit, iconDelete,icon_view_details;
        Button btnShowMap;

        public PaysViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            textDescription = itemView.findViewById(R.id.text_description);
            textCoordinates = itemView.findViewById(R.id.text_coordinates);
            imagePays = itemView.findViewById(R.id.image_pays);
            iconEdit = itemView.findViewById(R.id.icon_edit);
            iconDelete = itemView.findViewById(R.id.icon_delete);
            btnShowMap = itemView.findViewById(R.id.btn_show_map);
            icon_view_details=itemView.findViewById(R.id.icon_view_details);
        }
    }
}
