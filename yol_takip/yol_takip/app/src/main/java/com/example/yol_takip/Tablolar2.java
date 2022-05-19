package com.example.yol_takip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Tablolar2 extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablolar2);


        TextView textrota=(TextView) findViewById(R.id.text4);
        TextView textmaliyet=(TextView) findViewById(R.id.text5);
        ListView duraklar=(ListView)findViewById(R.id.list1);
        List<String> subjects = new ArrayList<>();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (getApplication(), android.R.layout.simple_list_item_1, android.R.id.text1, subjects);
        duraklar.setAdapter(adapter);



        db.collection("yoltakip").whereNotEqualTo("id",2)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Stations durak = document.toObject(Stations.class);
                                String subject = durak.getDurakAdi();
                                subjects.add(subject);

                            }
                            adapter.notifyDataSetChanged();

                        }
                    }
                });



        db.collection("rotalar").document("2")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String subject = document.getString("rota");
                                textrota.setText(subject);
                            }


                        }
                    }
                });

        db.collection("maliyetler").document("2")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String subject = document.getString("maliyet");
                                textmaliyet.setText(subject);
                            }


                        }
                    }
                });


    }
}