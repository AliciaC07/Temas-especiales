package com.aip.tarea_room.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.aip.tarea_room.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);
    @Update
    void update(Product product);

    @Query("DELETE FROM product_table")
    void deleteAll();

    @Delete
    void delete(Product product);


    @Query("SELECT * from product_table ORDER BY price ASC")
    LiveData<List<Product>> getAllProducts();






}
