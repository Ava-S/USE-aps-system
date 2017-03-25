/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author s156229
 */

/* TO-DO:
    1 - make a graphical user interface 
        a - make a jTable
    2 - connect component of GUI with variables of this
    3 - implement 1 scanners
        a - recognize product with ean
    4 - implement multiple scanners
        a - timing
        
 */
public class APS {

    List<Product> products;
    //This is a list of all the products that the customer buys
    //The key is the product he bought, 
    //and the value is the quantity of this product
    static Map<Product, Integer> shoppingList = new LinkedHashMap<>();
    Map<Product, Integer> tempShoppingList = new LinkedHashMap<>();
    Scanner input = new Scanner(System.in);
    MainFrame mainFrame = new MainFrame(this);
       

    void demo() {
        try {
            products = makeDatabase();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(APS.class.getName()).log(Level.SEVERE, null, ex);
        }
        //mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
        mainFrame.setTotalPrice(formatPrice(0) + "\n");
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.dispose();
        mainFrame.setUndecorated(true);
        mainFrame.setVisible(true);
    }

    void addProduct(Product product) {
        if (shoppingList.containsKey(product)) {
            tempShoppingList.put(product, shoppingList.get(product) + 1);
            shoppingList.remove(product);
            shoppingList.putAll(tempShoppingList);
        } else {
            shoppingList.put(product, 1);
        }
        mainFrame.showShoppingList(shoppingList);
    }

    List<Product> makeDatabase() throws FileNotFoundException {
        List<Product> database = new ArrayList<>();
        Scanner s = new Scanner(
                new File(
                        "C:\\Users\\s156229\\Documents\\GitHub\\USE-aps-system\\database.txt"));
        while (s.hasNext()) {
            String line = s.nextLine();
            String[] array = line.split(";");
            Product newProduct;
            newProduct = new Product(array[0], array[1], Double.parseDouble(
                    array[2]));
            database.add(newProduct);
        }
        return database;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new APS().demo();
    }

    public Product getProductByEan(String ean) throws IllegalArgumentException {
        System.out.println(ean);
        System.out.println(products);
        for (Product p
                : products) {
            String productEan = p.getEan();
            if (ean.equals(productEan)) {
                return p;
            }
        }
        //throw new IllegalArgumentException("product is not in database"); 
        return null;
    }

    void printShoppingList() {
        double total = 0;
        DecimalFormat f = new DecimalFormat("##.00");
        for (Entry<Product, Integer> entry
                : shoppingList.entrySet()) {
            Product p = entry.getKey();
            int quantity = entry.getValue();
            String name = p.getName();
            double price = p.getPrice();
            System.out.println(quantity + " | " + name + " | €" + quantity
                    * price);
            total += quantity * price;
        }

        System.out.println("−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−");
        System.out.println("Total price: €" + f.format(total).replace(",", "."));

    }

    public static String formatPrice(double value) {
        DecimalFormat f = new DecimalFormat("#0.00");
        return "€ " + f.format(value).replace(",", ".");
    }

    public String getTotal() {
        double total = 0;
        for (Entry<Product, Integer> entry
                : shoppingList.entrySet()) {
            Product p = entry.getKey();
            int quantity = entry.getValue();
            double price = p.getPrice();
            total += quantity * price;
        }
        return formatPrice(total);
    }

    static void emptyShoppingList() {
        shoppingList.clear();
        String[] args;
        args = new String[1];
        main(args);
    }
}
