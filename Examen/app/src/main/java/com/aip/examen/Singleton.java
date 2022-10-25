package com.aip.examen;

import java.util.ArrayList;

public class Singleton {


    public static ArrayList<Product> products = null;

    private static Singleton mInstance;
    private ArrayList<String> list = null;

    public static Singleton getInstance() {
        if(mInstance == null)
            mInstance = new Singleton();

        return mInstance;
    }

    private Singleton() {
        products = new ArrayList<Product>();
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void deleteProduct(String name){
        for (Product p : products
             ) {
            if (p.getName().equals(name)){
                products.remove(p);
            }

        }
    }

    public  ArrayList<Product> getProducts(){
        return products;
    }

}
