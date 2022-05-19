package com.example.yol_takip;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.yol_takip.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int toplamYolcu=0;
    int yolcuSayi=0;
    int say=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        db.collection("yoltakip")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Stations duraklar = document.toObject(Stations.class);

                                if(say==task.getResult().size()-1){
                                    int yolcuSayisi=duraklar.getYolcuSayisi();
                                    toplamYolcu=toplamYolcu+yolcuSayisi;
                                    servisHesapla(toplamYolcu);
                                }
                                else{
                                    int yolcuSayisi=duraklar.getYolcuSayisi();
                                    toplamYolcu=toplamYolcu+yolcuSayisi;
                                }
                                double konum1=Double.parseDouble(duraklar.getLatitude());
                                double konum2=Double.parseDouble(duraklar.getLongitude());
                                LatLng konumlar=new LatLng(konum1,konum2);
                                mMap.addMarker(new MarkerOptions().position(konumlar).title(duraklar.getDurakAdi()));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(konumlar));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.763457,29.928557), 9.0f));

                                say=say+1;
                            }

                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }



    public void servisHesapla(int yolcuSayisi){

        Toast.makeText(MapsActivity.this, String.valueOf(yolcuSayisi), Toast.LENGTH_SHORT).show();


    }

}