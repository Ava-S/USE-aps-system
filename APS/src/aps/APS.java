/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
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

    List<Product> products = new ArrayList<>();
    //This is a list of all the products that the customer buys
    //The key is the product he bought, 
    //and the value is the quantity of this product
    Map<Product, Integer> shoppingList = new LinkedHashMap<>();
    List<Product> sortedShoppingList = new ArrayList<>();
    Scanner input = new Scanner(System.in);
    MainFrame mainFrame = new MainFrame(this);
    String fileLocation;
    Boolean remove = false;
    private int productCounter = 0;
    private String scannedProduct;

    void demo() {
        //For all
        fileLocation = "./database.txt";
        try {
            products = makeDatabase();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(APS.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainFrame.setFirstTimePay();
        mainFrame.setFirstTimeRemove();
        mainFrame.setFirstTimeHelp();
        mainFrame.setFirstTimeActuallyRemove();
        mainFrame.setFirstTimeNewProduct();
        mainFrame.showTime();
        mainFrame.setTotalPrice(formatPrice(0) + "\n");
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.dispose();
        mainFrame.setUndecorated(true);
        mainFrame.setVisible(true);
    }

    void addProduct(Product product) {
        if (shoppingList.containsKey(product)) {
            shoppingList.put(product, shoppingList.get(product) + 1);
        } else {
            shoppingList.put(product, 1);
            sortedShoppingList.add(product);
        }

        mainFrame.showShoppingList();
        mainFrame.showTime();
    }

    void addNewProductFrame(String scannedString) {
        mainFrame.showNewProductFrame();
        this.scannedProduct = scannedString;
    }

    void addNewProduct(String name, String price) {
        try (FileWriter fw = new FileWriter(fileLocation, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println("");
            out.print(scannedProduct + ";" + name + ";" + price);
            Product thisProduct = new Product(scannedProduct, name, Double.parseDouble(price));

            products.add(thisProduct);
        } catch (IOException exception) {
            //exception handling left as an exercise for the reader
        }

        Product p = getProductByEan(scannedProduct);

        addProduct(p);
    }

    void removeProduct(Product product) {
        shoppingList.put(product, shoppingList.get(product) - 1);
        if (shoppingList.get(product) == 0) {
            sortedShoppingList.remove(product);
        }
        mainFrame.showShoppingList();
        mainFrame.showTime();
    }

    void removeProductAll(Product product) {
        shoppingList.remove(product);
        sortedShoppingList.remove(product);
        mainFrame.showShoppingList();
        mainFrame.showTime();
    }

    List<Product> makeDatabase() throws FileNotFoundException {
        List<Product> database = new ArrayList<>();
        Scanner s = new Scanner(
                new File(fileLocation));
        while (s.hasNext()) {
            String line = s.nextLine();

            if (line.trim().equalsIgnoreCase("")) {
                continue;
            }

            String[] array = line.split(";");
            Product newProduct = new Product(array[0], array[1], Double.parseDouble(
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

    public Product getProductByName(String name) throws IllegalArgumentException {
        for (Product p
                : products) {
            String productName = p.getName();
            if (name.equals(productName)) {
                return p;
            }
        }

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

    public void emptyShoppingList() {
        shoppingList.clear();
        sortedShoppingList.clear();
        mainFrame.showShoppingList();
    }

    public void setRemoveBoolean(boolean remove) {
        this.remove = remove;
    }

}
