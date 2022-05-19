package com.example.yol_takip.ui.routing;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yol_takip.MainActivity;
import com.example.yol_takip.MapsActivity;
import com.example.yol_takip.R;
import com.example.yol_takip.UserActivity;
import com.example.yol_takip.databinding.FragmentRoutingBinding;
import com.example.yol_takip.rota;
import com.example.yol_takip.rota2;
import com.example.yol_takip.verial;

import com.google.firebase.database.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RoutingFragment extends Fragment {

    private FragmentRoutingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routing,
                container, false);

        Button btnsinirli=(Button)view.findViewById(R.id.sinirliservis);
        Button btnsinirsiz=(Button)view.findViewById(R.id.sinirsizservis);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://192.168.178.14:5000/").build();




        btnsinirli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    // called if server is unreachable
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    // called if we get a
                    // response from the server
                    public void onResponse(
                            @NotNull Call call,
                            @NotNull Response response)
                            throws IOException {
                    }
                });


                Intent intent = new Intent(getActivity().getApplication(), rota.class);
                intent.putExtra("rota","2");
                startActivity(intent);
            }
        });
        btnsinirsiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    // called if server is unreachable
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    // called if we get a
                    // response from the server
                    public void onResponse(
                            @NotNull Call call,
                            @NotNull Response response)
                            throws IOException {
                    }
                });







                Intent intent = new Intent(getActivity().getApplication(), rota.class);
                intent.putExtra("rota","1");
                startActivity(intent);
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