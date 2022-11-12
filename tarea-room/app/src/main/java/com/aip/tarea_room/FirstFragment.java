package com.aip.tarea_room;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aip.tarea_room.databinding.FragmentFirstBinding;
import com.aip.tarea_room.model.Product;
import com.aip.tarea_room.model.ProductViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
//    private StorageReference storageReference;
//    private DatabaseReference databaseReference;

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
//        storageReference = FirebaseStorage.getInstance().getReference("uploads");
//        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        ItemAdapter adapter = new ItemAdapter();

        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
//        Product product = new Product();
//        product.setPrice(15.55f);
//        product.setBrand("Nike");
//        product.setName("Shirt");
//        productViewModel.insert(product);


        RecyclerView recyclerView = binding.recycleView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        productViewModel.findAll().observe(getViewLifecycleOwner(), products -> {
            adapter.setList(products);
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.registerProduct_Fragment);
            }
        });




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}