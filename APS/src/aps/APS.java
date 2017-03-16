/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import javax.swing.JFrame;

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
    
    Product[] products = makeDatabase();
    //This is a list of all the products that the customer buys
    //The key is the product he bought, 
    //and the value is the quantity of this product
    Map<Product, Integer> shoppingList = new HashMap<>();  
    Scanner input = new Scanner(System.in);
    MainFrame mainFrame = new MainFrame(this); 
    
    void demo() {
        addProduct(getProductByEan("20005825"));
        addProduct(products[0]);
        addProduct(products[2]);
        addProduct(products[17]);
        addProduct(products[17]);
                        
        mainFrame.setVisible(true);
    }
    
    void addProduct(Product product){
        if (shoppingList.containsKey(product)){
            shoppingList.put(product, shoppingList.get(product) + 1);
        } else {
            shoppingList.put(product, 1);
        }
        mainFrame.showShoppingList(shoppingList);
    }
    
    Product[] makeDatabase(){
        Product[] database = new Product[21];
        database[0] = new Product("20005825", "Fin Carre Melkchocolade", 0.49);
        database[1] = new Product("20151737", "Fair Globe Pure Chocolade", 0.99);
        database[2] = new Product("20368197","Fin Carré Witte Chocolade", 0.49);
        database[3] = new Product("87157260","Heinz Tomato ketchup topdown", 2.48);
        database[4] = new Product("4084500636293L", "Dreft afwasmiddel aloe-komkommer", 2.29);
        database[5] = new Product("5410056195349L", "Devos Lemmens Mayonaise", 2.99);
        database[6] = new Product("8718906074125L", "AH Delicata chocolade extra puur 72%", 0.99);
        database[7] = new Product("5410091711856","Witte Reus Keukenreiniger", 2.79);
        database[8] = new Product("5412322650002","Jacques Matinettes puur", 1.89);
        database[9] = new Product("8000430900231","Galbani Mozzarella di latte di bufalo", 2.31);
        database[10] = new Product("8002270014901","San Pellegrino Mineraalwater 6x1l", 2.29);
        database[11] = new Product("20151737", "Fair Globe Pure Chocolade", 0.99);
        database[12] = new Product("8710400165385","AH Olijfolie Extra Vierge 1l", 5.85);
        database[13] = new Product("8710400243748","AH Frambozen", 1.99);
        database[14] = new Product("8710400456766","AH Oosterse wok olie", 2.59);
        database[15] = new Product("8710400688105","AH Biologisch Kikkererwten", 0.89);
        database[16] = new Product("8710400842895","Tivall Vega falafel", 2.89);
        database[17] = new Product("8711000351116","DE Espresso Oploskoffie", 3.99);
        database[18] = new Product("8718265572737","AH Kruidkoekrepen", 1.19);
        database[19] = new Product("8718906074118","AH Delicata puur 85%", 0.99);
        database[20] = new Product("5449000129918", "Nestea Green Tea", 0.70);
        return database;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new APS().demo();
    }
    
    public Product getProductByEan(String ean) throws IllegalArgumentException{
        System.out.println(ean);
        System.out.println(products);
        for (Product p: products){
            String productEan = p.getEan(); 
            if(ean.equals(productEan)){
                return p;
            }
        }
        //throw new IllegalArgumentException("product is not in database"); 
        return null;
    }
    
    void printShoppingList() {
    double total = 0;
    DecimalFormat f = new DecimalFormat("##.00");
        for (Entry<Product, Integer> entry : shoppingList.entrySet()) {
            Product p = entry.getKey();
            int quantity = entry.getValue();
            String name = p.getName();
            double price = p.getPrice(); 
            System.out.println(quantity + " | " + name + " | €" + quantity*price);
            total += quantity*price;            
        }
        
    System.out.println( "−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−" );
    System.out.println( "Total price: €" + f.format(total).replace(",", "."));
    
    }
    
    public static String formatPrice(double value){
        DecimalFormat f = new DecimalFormat("#0.00");
        return "€ " + f.format(value).replace(",", ".");
    }
    
    public String getTotal(){
        double total = 0; 
        for (Entry<Product, Integer> entry : shoppingList.entrySet()) {
            Product p = entry.getKey();
            int quantity = entry.getValue();
            double price = p.getPrice(); 
            total += quantity*price;            
        }
        return formatPrice(total); 
    }
    
}



