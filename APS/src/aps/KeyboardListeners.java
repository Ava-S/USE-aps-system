/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author s156229
 */
public class KeyboardListeners implements KeyListener {

    public String scannedString = "";
    public APS aps;

    KeyboardListeners(APS aps) {
        this.aps = aps;
    }

    long time = 0;
    long newTime = 0;
    String productEan = "";
    Scanner sc = new Scanner(System.in);

    @Override
    public void keyTyped(KeyEvent e) {

        int code = (int) e.getKeyChar();

        if (code == 10) {
            newTime = System.currentTimeMillis();
            if (productEan.equals(scannedString) && newTime - time < 1000) {
                scannedString = "";
                return;
            }

            Product p = aps.getProductByEan(scannedString);
            time = newTime;
            productEan = scannedString;

            if (p != null) {
                aps.addProduct(p);
            } else {
                System.out.println(
                        "The product you scanned in not in our database");
                System.out.println("The barcode is " + scannedString);
                System.out.println("What is the name of your product?");
                String name = sc.nextLine();
                System.out.println("What is the price of your product?");
                String price = sc.nextLine();
                System.out.println("The data you put in is this: "
                        + scannedString + " " + name + " " + price);

                try (FileWriter fw = new FileWriter(aps.fileLocation, true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw)) {
                    out.println("");
                    out.print(scannedString + "; " + name + "; " + price);
                    Product thisProduct = new Product(scannedString, name, Double.parseDouble(price));
                    
                    aps.products.add(thisProduct);
                } catch (IOException exception) {
                    //exception handling left as an exercise for the reader
                }
                
                p = aps.getProductByEan(scannedString);
                
                System.out.println("P: " + p);
                aps.addProduct(p);
            }

            scannedString = "";
        } else {
            scannedString += e.getKeyChar();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
