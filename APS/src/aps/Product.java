/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

import java.text.DecimalFormat;

/**
 *
 * @author s156229
 */
public class Product {

    private final String ean;
    private final String name;
    private final double price;

    Product(String ean, String name, double price) {
        this.ean = ean;
        this.name = name;
        this.price = price;
    }

    void print() {
        System.out.println("Product ean   " + ean);
        System.out.println("name:" + name);
        System.out.println("price: $ " + price);
    }
    
    public String getEan(){
        return ean; 
    }
    
    public String getName(){
        return name; 
    }
    
    public double getPrice(){
        return price; 
    }
    
}
