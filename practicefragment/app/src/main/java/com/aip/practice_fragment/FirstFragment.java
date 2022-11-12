package com.aip.practice_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.aip.practice_fragment.databinding.FragmentFirstBinding;

import java.time.LocalDate;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("pucmm", Context.MODE_PRIVATE);


        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.txtMessage.getText().toString();
                String lastName = binding.txtLastname.getText().toString();
                String id = binding.txtId.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", name);
                editor.putString("lastName", lastName);
                editor.putString("id", id);
                editor.apply();
                clear();



            }
        });
        binding.btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = sharedPreferences.getString("name", "Not found");
                String lastName = sharedPreferences.getString("lastName", "Not found");
                String id = sharedPreferences.getString("id", "Not found");

                binding.txtMessage.setText(name);
                binding.txtLastname.setText(lastName);
                binding.txtId.setText(id);

            }
        });
        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void clear(){
        binding.txtMessage.setText("");
        binding.txtLastname.setText("");
        binding.txtId.setText("");
    }

}