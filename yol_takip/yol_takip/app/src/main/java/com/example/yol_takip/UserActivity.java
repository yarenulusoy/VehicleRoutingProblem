package com.example.yol_takip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yol_takip.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Button btnTalep=(Button) findViewById(R.id.btn_talep);
        Button btnRota=(Button) findViewById(R.id.btn_rota);
        Spinner spinDurak=(Spinner) findViewById(R.id.spinDurak);

        List<String> subjects = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDurak.setAdapter(adapter);


        db.collection("yoltakip").whereNotEqualTo("id", 1)
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

        btnTalep.setOnClickListener(new View.OnClickListener() {
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
                                                .update("yolcuSayisi",duraklar.getYolcuSayisi()+1)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(UserActivity.this, "Yolcu Eklendi.", Toast.LENGTH_SHORT).show();
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







                Intent intent = getIntent();
                String data = intent.getStringExtra("kullanici_id");
                Map<String, Object> veri = new HashMap<>();
                veri.put("durak",  spinDurak.getSelectedItem().toString());



                db.collection("talepler").document(data)
                        .set(veri, SetOptions.merge());




            }
        });


        btnRota.setOnClickListener(new View.OnClickListener() {
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

                                        db.collection("rotalar").document("1")
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                String subject = document.getString("rota");
                                                                boolean b;
                                                                b = subject.contains(duraklar.getLatitude());
                                                                if (b == true) {
                                                                    Toast.makeText(UserActivity.this, "ALINDINIZ", Toast.LENGTH_SHORT).show();
                                                                }
                                                                else{
                                                                    Toast.makeText(UserActivity.this, "ALINMADINIZ", Toast.LENGTH_SHORT).show();
                                                                }


                                                            }


                                                        }
                                                    }
                                                });
                                    }
                                }
                            }
                        });











            }
        });

    }
}