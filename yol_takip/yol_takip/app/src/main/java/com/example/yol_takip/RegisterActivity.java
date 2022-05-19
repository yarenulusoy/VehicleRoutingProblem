package com.example.yol_takip;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btnKayit = (Button) findViewById(R.id.btn_giris);
        EditText kAdiText = (EditText) findViewById(R.id.kullanici_adi);
        EditText sifreText = (EditText) findViewById(R.id.sifre);
        EditText sifreText2 = (EditText) findViewById(R.id.sifre2);
        ref = db.collection("kullanicilar").document();


        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kAdiText.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Kullanıcı adı girin", Toast.LENGTH_SHORT).show();


                }else if(sifreText.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "Şifre girin", Toast.LENGTH_SHORT).show();

                }else if(sifreText2.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Şifre girin", Toast.LENGTH_SHORT).show();


                }else if(!sifreText.getText().toString().equals(sifreText2.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Şifreler eşleşmiyor.", Toast.LENGTH_SHORT).show();
                }
                else{
                    ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists())
                            {
                                Toast.makeText(RegisterActivity.this, "Bu kullanıcı mevcut", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Map<String, Object> reg_entry = new HashMap<>();
                                reg_entry.put("kullaniciAdi", kAdiText.getText().toString());
                                reg_entry.put("sifre",sifreText.getText().toString());

                                db.collection("kullanicilar")
                                        .add(reg_entry)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(RegisterActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Error", e.getMessage());
                                    }
                                });
                            }

                        }
                    });
                }


            }
        });
    }
}
