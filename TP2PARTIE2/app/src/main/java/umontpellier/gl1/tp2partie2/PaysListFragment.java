package umontpellier.gl1.tp2partie2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PaysListFragment extends Fragment {

    private RecyclerView recyclerView;
    private PaysAdapter paysAdapter;
    private DatabaseHelperPays dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pays_list, container, false);

        dbHelper = new DatabaseHelperPays(getActivity());
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<Pays> paysList = dbHelper.getAllPays();
        paysAdapter = new PaysAdapter(getActivity(), paysList, dbHelper,true);
        recyclerView.setAdapter(paysAdapter);

        return view;
    }
}