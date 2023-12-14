import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

public class Customer implements Serializable {
    String name;
    HashMap<Product,Integer> countingpro = new HashMap<Product,Integer>();
    double spent;
    ElectronicStore electronicStore;

    public Customer(String n){
        name = n;
        spent = 0.0;
    }

    public double getSoldQ(){return spent;}
    public void setSoldQ(double q){this.spent = q;}

    public String getName(){return name;}

    public void addToCustomerShopping(Product p, int amount) {
        if (!this.countingpro.containsKey(p)) {
            this.countingpro.put(p, amount);
            spent=spent+(amount*p.getPrice());
        }
        else  {
            int am = countingpro.get(p);
            this.countingpro.replace(p, am, (am + amount));
            spent= spent-(am*p.getPrice());
            spent=spent+((am+amount)*p.getPrice());

        }
    }



    public String toString(){
        return name +" who has spent $" + spent;
    }

    public void printPurchaseHistory(){
        for(Map.Entry enter : countingpro.entrySet()){
            System.out.println(enter.getValue() + " x " + enter.getKey().toString());
        }
    }


}
