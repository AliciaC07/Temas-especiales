package com.aip.tarea_room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aip.tarea_room.databinding.FragmentFirstBinding;
import com.aip.tarea_room.model.Product;
import com.aip.tarea_room.model.ProductViewModel;

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
        ItemAdapter adapter = new ItemAdapter();

        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);


        RecyclerView recyclerView = binding.recycleView;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        ItemAdapter itemAdapter = (ItemAdapter) recyclerView.getAdapter();
        itemAdapter.setOnClickListener((itemView, position) -> {
            Product p = adapter.getProducts().get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("product", p);
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.editFragment, bundle);
            Toast.makeText(binding.getRoot().getContext(), p.getName() + " was clicked!", Toast.LENGTH_SHORT).show();

        });

        productViewModel.findAll().observe(getViewLifecycleOwner(), products -> {
            adapter.setList(products);
        });

        binding.btnAdd.setOnClickListener(view1 -> NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.registerProduct_Fragment));




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}