package com.example.yol_takip.ui.out;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yol_takip.MainActivity;
import com.example.yol_takip.R;
import com.example.yol_takip.databinding.FragmentHomeBinding;

public class OutFragment extends Fragment {
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_out,
                container, false);
        Intent intent = new Intent(getActivity().getApplication(), MainActivity.class);
        startActivity(intent);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}