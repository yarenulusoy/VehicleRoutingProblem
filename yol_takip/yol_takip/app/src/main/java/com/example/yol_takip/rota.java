package com.example.yol_takip;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yol_takip.harita.FetchURL;
import com.example.yol_takip.harita.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class rota extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    GoogleMap mMap;
    Button getDirection;
    int deger=0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Double>rotalar = new ArrayList();
    List<Double>rotalar2 = new ArrayList();
    List<Double>konumlar = new ArrayList();

    private Polyline currentPolyline;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota);
        getDirection = findViewById(R.id.btnGetDirection);
        Button tablo=findViewById(R.id.btnTablo);
        TextView txt=(TextView) findViewById(R.id.text);

        Intent intent = getIntent();
        String data = intent.getStringExtra("rota");



        DocumentReference docRef = db.collection("rotalar").document(data);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {


                        String rota=document.getData().values().toString();

                        String rota1=rota.replace("[", "");
                        String rota2=rota1.replace("]", "");
                        String rota3=rota2.replace(" ", "");
                        String rota4=rota3.replace("'", "");
                        String[] srotalar = rota4.split(",");

                        for(int i=0;i<srotalar.length;i++){
                            rotalar.add(Double.valueOf(srotalar[i]));
                        }

                    }
                }
            }
        });

        tablo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String data = intent.getStringExtra("rota");

                Intent giris = new Intent(rota.this,Tablolar.class);
                    giris.putExtra("id",data);
                    startActivity(giris);



            }
        });
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                for (int i = 0; i < rotalar.size()-2; i += 2) {
                    if(i!=0){
                        if(rotalar.get(i)==40.823371 || rotalar.get(i+2)==40.823371) {

                            i=i+2;
                            new FetchURL(rota.this).execute(getUrl(new MarkerOptions().position(new LatLng(rotalar.get(i), rotalar.get(i+1))).
                                            getPosition(), new MarkerOptions().position(new LatLng(rotalar.get(i+2), rotalar.get(i+3))).getPosition(),
                                    "driving"), "driving");
                        }
                        else{

                            new FetchURL(rota.this).execute(getUrl(new MarkerOptions().position(new LatLng(rotalar.get(i), rotalar.get(i+1))).
                                            getPosition(), new MarkerOptions().position(new LatLng(rotalar.get(i+2), rotalar.get(i+3))).getPosition(),
                                    "driving"), "driving");
                        }
                    }
                    else{

                        new FetchURL(rota.this).execute(getUrl(new MarkerOptions().position(new LatLng(rotalar.get(i), rotalar.get(i+1))).
                                        getPosition(), new MarkerOptions().position(new LatLng(rotalar.get(i+2), rotalar.get(i+3))).getPosition(),
                                "driving"), "driving");
                    }

                }
            }

        });


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        db.collection("yoltakip")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Stations duraklar = document.toObject(Stations.class);
                                konumlar.add(Double.valueOf(duraklar.getLatitude()));
                                konumlar.add(Double.valueOf(duraklar.getLongitude()));

                            }
                            mMap = googleMap;
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.763457,29.928557), 9.0f));

                            // Double[] array={40.823371,29.9254,40.763457,29.928557,40.6298,29.9509,40.823371,29.9254,41.070363,30.152394,40.823371,29.9254,40.83614210,29.38310260};
                            //Double[] array={40.823371,29.9254,40.7597,29.3856,40.83614210,29.38310260,40.6298,29.9509,40.756189,29.830918};
                            for (int i=0; i<konumlar.size()-1; i+=2) {
                                mMap.addMarker(new MarkerOptions().position(new LatLng(konumlar.get(i),konumlar.get(i+1))));

                            }

                        }
                    }
                });



    }

    private String getUrl(LatLng origin, LatLng dest,String directionMode) {

        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;


    }


    @Override
    public void onTaskDone(Object... values) {

        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
        currentPolyline.setColor(Color.GREEN);
        if(deger==1){
            currentPolyline.setColor(Color.BLUE);
        }
        if(deger==2){
            currentPolyline.setColor(Color.GREEN);
        }
        if(deger==3){
            currentPolyline.setColor(Color.YELLOW);
        }
        if(deger==4){
            currentPolyline.setColor(Color.GRAY);
        }

    }


}

