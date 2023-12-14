//Class representing an electronic store
//Has an array of products that represent the items the store can sell
import javax.naming.ldap.PagedResultsResponseControl;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ElectronicStore implements Serializable{
  //public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
  private int curProducts;
  private String name;
  private ArrayList<Product> stock = new ArrayList<>(); //Array to hold all products
  private double revenue;
  private ArrayList<Customer> customers = new ArrayList<>();
  private int cusCount = 0;
  ObjectOutputStream out;
  
  public ElectronicStore(String initName){
    revenue = 0.0;
    name = initName;
    this.stock = stock;
    //stock = new Product[MAX_PRODUCTS];
    curProducts = 0;
  }
  
  public String getName(){
    return name;
  }
  
  //Adds a product and returns true if there is space in the array
  //Returns false otherwise
  public boolean addProduct(Product newProduct){
    if(!stock.contains(newProduct)){
      stock.add(newProduct);
      curProducts++;
      return true;
      }
      return false;
  }

 public boolean registerCustomer(Customer c){
    for(Customer cus: customers){
      if(cus.getName().equalsIgnoreCase(c.getName())){
        return false;
      }
    }
    cusCount ++;
    customers.add(c);
    return true;
  }

  public ArrayList<Customer> getCustomers(){
    return customers;
  }

  public ArrayList<Product> searchProducts(String x){
    ArrayList<Product> pro = new ArrayList<>();
    String v = x.toLowerCase();
    for (Product product: stock){
        if(product.toString().toLowerCase().contains(v)){
            pro.add(product);
        }
    }
    return pro;
  }

  public ArrayList<Product> searchProducts(String x, double minPrice, double maxPrice){
    ArrayList<Product> pro = searchProducts(x);
    if(minPrice > 0 && maxPrice < 0){
        ArrayList<Product> p = new ArrayList<>();
        for (Product product: pro){
            if(product.getPrice() >= minPrice){
             p.add(product);
            }
        }
        return p;
    }
    else if(minPrice >0 && maxPrice >0) {
        ArrayList<Product> p = new ArrayList<>();
        for (Product product : pro) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                p.add(product);
            }
        }
        return p;
    }
    else if(minPrice <0 && maxPrice >0) {
        ArrayList<Product> p = new ArrayList<>();
        for (Product product : pro) {
            if (product.getPrice() <= maxPrice) {
                p.add(product);
            }
        }
        return p;
    }
    else
        return pro;

  }

  public boolean addStock(Product p, int amount){
    if(stock.contains(p) && amount >0){
        p.setStockQuantity(p.getStockQuantity() + amount);
        return true;
    }
    return false;
  }

    public boolean sellProduct(Product p, Customer c, int amount) {

        if (((!stock.contains(p)) || (!customers.contains(c))) || ((!stock.contains(p)) && (!customers.contains(c)))) {
            return false;
        } else if (p.sellUnits(amount) <= 0.0) {
            return false;
        } else
            c.addToCustomerShopping(p, amount);
        return true;
    }




    public ArrayList<Customer> getTopXCustomers(int x)
    {
        ArrayList<Customer> copy= customers;
        Comparator<Customer> com= new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                if(o1.spent<o2.spent){
                    return 1;
                }
                return -1;
            }
        };
        Collections.sort(customers,com);
        ArrayList<Customer> c=new ArrayList<>();
        if(x>customers.size())
        {
            return copy;
        }
        else if(x>0 &&x<customers.size())
        {
            for (int i=0;i<x;i++)
            {
                c.add(copy.get(i));
            }
            return c;
        }
        else {
            return c;
        }
    }

  public boolean saveToFile(String filename) throws IOException, ClassNotFoundException {
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
      objectOutputStream.writeObject(this);
      objectOutputStream.close();
      return true;

    }

  public static ElectronicStore loadFromFile(String filename) throws IOException, ClassNotFoundException {
      ElectronicStore electronicStore;
      ObjectInputStream objectInputStream;
      try {
           objectInputStream = new ObjectInputStream(new FileInputStream(filename));
           electronicStore = (ElectronicStore) objectInputStream.readObject();
           objectInputStream.close();

      } catch (FileNotFoundException e) {
          return null;
      }
      return electronicStore;
  }
  }

