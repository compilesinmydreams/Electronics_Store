import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

//Base class for all products the store will sell
public abstract class Product implements Serializable {
 private double price;
 private int stockQuantity;
 private int soldQuantity;
 
 public Product(double initPrice, int initQuantity){
   price = initPrice;
   stockQuantity = initQuantity;
 }

 public int getStockQuantity(){
   return stockQuantity;
 }
 public void setStockQuantity(int s){ this.stockQuantity = s;}


 public int getSoldQuantity(){
   return soldQuantity;
 }
 public void setSoldQuantity(int sq){this.soldQuantity = sq;}
 
 public double getPrice(){
   return price;
 }

 /*public void saveTo(PrintWriter aFile){
     aFile.println(price);
     aFile.println(soldQuantity);
     aFile.println(stockQuantity);
    }*/

    /*public static Product loadFrom(BufferedReader aFile) throws IOException{
     Double price = Double.parseDouble(aFile.readLine());
     int soldq = Integer.parseInt(aFile.readLine());
     int stockq = Integer.parseInt(aFile.readLine());
 }*/

    //Returns the total revenue (price * amount) if there are at least amount items in stock
 //Return 0 otherwise (i.e., there is no sale completed)
 public double sellUnits(int amount){
   if(amount > 0 && stockQuantity >= amount){
     stockQuantity -= amount;
     soldQuantity += amount;
     return price * amount;
   }
   return 0.0;
 }
}