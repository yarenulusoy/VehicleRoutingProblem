package com.example.yol_takip.ui.pass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yol_takip.R;
import com.example.yol_takip.Stations;
import com.example.yol_takip.databinding.FragmentPassBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class PassFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FragmentPassBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pass,
                container, false);

        Button btnYolcu = (Button) view.findViewById(R.id.btn_yolcu);
        EditText yolcuSayisi=(EditText)view.findViewById(R.id.yolcuSayisi);
        Spinner spinDurak=(Spinner) view.findViewById(R.id.spinDurak);


        List<String> subjects = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDurak.setAdapter(adapter);

        db.collection("yoltakip").whereNotEqualTo("id",1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String subject = document.getString("durakAdi");
                                subjects.add(subject);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        btnYolcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("yoltakip")
                        .whereEqualTo("durakAdi", spinDurak.getSelectedItem().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Stations duraklar = document.toObject(Stations.class);
                                        DocumentReference docRef = db.collection("yoltakip").document(document.getId());

                                        docRef
                                                .update("yolcuSayisi",Integer.parseInt(yolcuSayisi.getText().toString()))
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getActivity(), "Yolcu Eklendi.", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Log.w(TAG, "Error updating document", e);
                                                    }
                                                });


                                    }
                                } else {
                                    //Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });


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