package com.example.yol_takip.ui.station;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yol_takip.R;
import com.example.yol_takip.Stations;
import com.example.yol_takip.databinding.FragmentStationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class StationFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StationViewModel stationViewModel;
    private FragmentStationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station,
                container, false);
        Button buttonDurak = (Button) view.findViewById(R.id.btn_durak_ekle);
        Button button = (Button) view.findViewById(R.id.btn2);
        Button button2= (Button) view.findViewById(R.id.btn3);
        EditText durakAdi=(EditText)view.findViewById(R.id.durak_adi);
        EditText durakLat=(EditText)view.findViewById(R.id.lat);
        EditText durakLong=(EditText)view.findViewById(R.id.lang);
        EditText maliyet=(EditText)view.findViewById(R.id.maliyet);
        EditText kapasite=(EditText)view.findViewById(R.id.kapasite);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference docRef = db.collection("adminveriler").document("maliyet");
                docRef.update("mali", Integer.valueOf(maliyet.getText().toString()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Guncellendi", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef = db.collection("adminveriler").document("kapasite");
                docRef.update("kapa", Integer.valueOf(kapasite.getText().toString()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Guncellendi", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        buttonDurak.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Map<String, Object> data = new HashMap<>();


                db.collection("yoltakip").orderBy("id", Query.Direction.DESCENDING).limit(1)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Stations durak = document.toObject(Stations.class);
                                        int subject= durak.getId();
                                        data.put("id",subject+1);
                                        data.put("durakAdi", durakAdi.getText().toString());
                                        data.put("latitude", durakLat.getText().toString());
                                        data.put("longitude", durakLong.getText().toString());
                                        data.put("yolcuSayisi",0);

                                        db.collection("yoltakip").add(data)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(getActivity(), "Durak Eklendi.", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });



                                    }
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