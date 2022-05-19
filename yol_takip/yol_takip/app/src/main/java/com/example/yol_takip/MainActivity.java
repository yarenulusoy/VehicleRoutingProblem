package com.example.yol_takip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.Security;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.btn_giris);
        Button btnKayit = (Button) findViewById(R.id.btn_kayit);
        EditText kAdiText = (EditText) findViewById(R.id.kullanici_adi);
        EditText sifreText = (EditText) findViewById(R.id.sifre);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("kullanicilar")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String a = doc.getString("kullaniciAdi");
                                String b = doc.getString("sifre");
                                if (a.equalsIgnoreCase(kAdiText.getText().toString()) & b.equalsIgnoreCase(sifreText.getText().toString())) {
                                    db.collection("kullanicilar").whereEqualTo("kullaniciAdi", kAdiText.getText().toString()).get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            if (document.getId().equals("1")) {
                                                                Intent giris = new Intent(MainActivity.this,MainMenu.class);
                                                                startActivity(giris);
                                                                Toast.makeText(MainActivity.this, "Giriş Başarılı.", Toast.LENGTH_SHORT).show();

                                                            } else {

                                                                Intent sayfaGiris = new Intent(MainActivity.this, UserActivity.class);
                                                                sayfaGiris.putExtra("kullanici_id",document.getId());
                                                                startActivity(sayfaGiris);
                                                                Toast.makeText(MainActivity.this, "Giriş Başarılı.", Toast.LENGTH_SHORT).show();

                                                            }
                                                        }
                                                    }
                                                }
                                            });
                                }

                            }
                        }
                    }
                });
            }
        });

        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sayfaGiris = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(sayfaGiris);
            }
        });
    }
}