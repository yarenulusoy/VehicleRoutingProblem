package com.example.yol_takip.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.yol_takip.R;
import com.example.yol_takip.Stations;
import com.example.yol_takip.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FragmentHomeBinding binding;
    final List<Stations> duraklar = new ArrayList<Stations>();
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);
        ListView duraklar=(ListView) view.findViewById(R.id.list_view);
        List<String> subjects = new ArrayList<>();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, subjects);
        ListView talepler=(ListView) view.findViewById(R.id.list_view2);
        duraklar.setAdapter(adapter);
        List<String> subjects2 = new ArrayList<>();
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, subjects2);


        talepler.setAdapter(adapter2);
        db.collection("yoltakip").whereNotEqualTo("id",1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Stations durak = document.toObject(Stations.class);
                                String subject = durak.getDurakAdi();
                                String subject2 = String.valueOf(durak.getYolcuSayisi());
                                subjects.add(subject);
                                subjects2.add(subject2);
                            }
                            adapter.notifyDataSetChanged();
                            adapter2.notifyDataSetChanged();
                        }
                    }
                });




     return view;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}