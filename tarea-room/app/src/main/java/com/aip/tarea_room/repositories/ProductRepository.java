package com.aip.tarea_room.repositories;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.aip.tarea_room.database.AppDatabase;
import com.aip.tarea_room.database.ProductDao;
import com.aip.tarea_room.model.Product;

import java.util.List;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<Product>> mAllProduct;



    public ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        productDao = db.productDao();
        mAllProduct = productDao.getAllProducts();

    }

    public void insert(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                productDao.insert(product));
    }
    public void update(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                productDao.update(product));
    }
    public void delete(Product product){
        AppDatabase.databaseWriteExecutor.execute(()->{
            productDao.delete(product);
        });
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() ->
                productDao.deleteAll());
    }

    public LiveData<List<Product>> findAll() {
        return mAllProduct;
    }

}
