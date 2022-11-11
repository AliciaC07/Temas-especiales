package com.aip.tarea_room.model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.aip.tarea_room.repositories.ProductRepository;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository productRepository;

    private LiveData<List<Product>> productAll;


    public ProductViewModel(@NonNull @NotNull Application application) {
        super(application);

        productRepository = new ProductRepository(application);

        productAll = productRepository.findAll();

    }

    public void insert(Product product) {
        productRepository.insert(product);
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }

    public LiveData<List<Product>> findAll() {
        return productAll;
    }



}
