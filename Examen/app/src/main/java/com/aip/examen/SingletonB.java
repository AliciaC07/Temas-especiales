package com.aip.examen;

import java.util.ArrayList;

public class SingletonB {
    private static volatile SingletonB INSTANCE = null;
    public static ArrayList<Product> products = null;

    private SingletonB() {
        Product p = new Product("Cerveza", "La tuya", 70.89f);
        products = new ArrayList<Product>();
        products.add(p);
    }

    public static SingletonB getInstance() {
        if(INSTANCE == null) {
            synchronized (SingletonB.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SingletonB();
                }
            }
        }
        return INSTANCE;
    }
    public  ArrayList<Product> getProducts(){
        return products;
    }
    public void addProduct(Product product){
        products.add(product);
    }
    public Boolean repeat(String name){
        Product p = find(name);
        if (p == null){
            return true;
        }
        return false;
    }

    public Product find(String name){
        for (Product p : products
             ) {
            if (p.getName().equals(name)){
                return p;
            }

        }
        return null;
    }

}
