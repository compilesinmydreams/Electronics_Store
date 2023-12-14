import java.io.IOException;

public class Assignment4Tester {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ElectronicStore store = new ElectronicStore("Past Shop");

        Product p1 = new Desktop(1000, 10, 4, 32, true, 256, "Full Size");
        store.addProduct(p1);
        store.addProduct(new Desktop(800, 25, 3, 16, false, 2048, "Mid Size"));
        store.addProduct(new Desktop(700, 15, 2.5, 16, false, 1024, "Compact"));
        Product p2 = new Desktop(1200, 5, 4.5, 64, true, 512, "Server");
        store.addProduct(p2);

        store.addProduct(new Laptop(1500, 10, 3.5, 16, true, 256, 15.1));
        store.addProduct(new Laptop(2500, 3, 4, 32, true, 512, 35.1));
        store.addProduct(new Laptop(1200, 12, 3, 16, false, 512, 15.1));
        Product p3 = new Laptop(800, 5, 2.5, 8, true, 128, 13.1);
        store.addProduct(p3);

        store.addProduct(new Fridge(1500, 15, 250, "Stainless Steel", "Danby", 15.5, true));
        store.addProduct(new Fridge(2500, 5, 900, "Shiny Blue", "Mr Freeze", 25, true));
        Product p4 = new Fridge(3018, 1, 111, "Red", "Horn Pass", 2, true);
        store.addProduct(p4);
        store.addProduct(new Fridge(250, 30, 100, "White", "Discount Freezer Corporation", 5, false));
        store.addProduct(new Fridge(500, 13, 275, "Off White", "Discount Freezer Corporation", 15, false));

        store.addProduct(new ToasterOven(80, 10, 50, "Red", "General Electric", 5, false));
        store.addProduct(new ToasterOven(50, 5, 40, "Red", "General Electric", 3, false));
        Product p5 = new ToasterOven(2000, 25, 400, "Gold", "Toast-O-Max Bluetooth-Enabled Toasters", 3, true);
        store.addProduct(p5);

        Customer c1 = new Customer("Frodo");
        Customer c2 = new Customer("Galadriel");
        Customer c3 = new Customer("Tom B.");

        store.registerCustomer(c1);
        store.registerCustomer(c2);
        store.registerCustomer(new Customer("Eowyn"));
        store.registerCustomer(c3);
        store.registerCustomer(new Customer("Shadowfax"));
        store.registerCustomer(new Customer("Galadriel"));
        store.registerCustomer(new Customer("Lobelia"));
        store.registerCustomer(new Customer("Bert"));
        store.registerCustomer(new Customer("Shadowfax"));
        store.registerCustomer(new Customer("William"));
        store.registerCustomer(new Customer("Lobelia"));
        System.out.println("# customers (should be 8): " + store.getCustomers().size());

        //Testing size of list returned by product search
        System.out.println("# matched products (should be 3): " + store.searchProducts("red").size());
        System.out.println("# matched products (should be 5): " + store.searchProducts("SSD").size());
        System.out.println("# matched products (should be 8): " + store.searchProducts("ram").size());
        System.out.println("# matched products (should be 1): " + store.searchProducts("red", 55, 100).size());
        System.out.println("# matched products (should be 2): " + store.searchProducts("red", -1, 100).size());
        System.out.println("# matched products (should be 2): " + store.searchProducts("red", 55, -1).size());
        System.out.println("# matched products (should be 2): " + store.searchProducts("ssd", -1, 1100).size());
        System.out.println("# matched products (should be 3): " + store.searchProducts("ssd", 1050, -1).size());

        store.sellProduct(p1, c1, 5);
        store.sellProduct(p1, c2, 4);
        store.sellProduct(p1, c3, 1);
        store.sellProduct(p1, c3, 5);

        System.out.println("Printing top 3 customers");
        System.out.println("Should be: Frodo (5000), Galadriel (4000), Tom B. (1000)");
        for(Customer c: store.getTopXCustomers(3)){
            System.out.println(c);
        }

        store.sellProduct(p4, c3, 1);
        store.sellProduct(p5, c3, 3);
        store.sellProduct(p5, c3, 2);
        store.sellProduct(p3, c1, 4);
        store.sellProduct(p3, c2, 2);

        System.out.println("Printing top 3 customers");
        System.out.println("Should be: Tom B. (14018), Frodo (8200), Galadriel (4000)");
        for(Customer c: store.getTopXCustomers(3)){
            System.out.println(c);
        }

        System.out.println("Printing Tom B.'s purchases");
        System.out.println("Should have 1xDesktop, 1xFridge, 5xToasters");
        c3.printPurchaseHistory();

        //Try to sell some products that don't exist:
        Fridge mysteryFridge = new Fridge(1500, 5, 200, "Purple", "Fancy Freezers", 15, false);
        store.sellProduct(mysteryFridge, c3, 0);
        System.out.println("Printing Tom B.'s purchases");
        System.out.println("Should have 1xDesktop, 1xFridge, 5xToasters");
        c3.printPurchaseHistory();

        //Try to sell some products with unregistered users
        Customer newCustomer = new Customer("Fredigar");
        store.sellProduct(p2, newCustomer, 5);
        System.out.println("Printing top 3 customers");
        System.out.println("Should be: Tom B. (14018), Frodo (8200), Galadriel (4000)");
        for(Customer c: store.getTopXCustomers(3)){
            System.out.println(c);
        }

        System.out.println("Registering new user and retrying previous sell step...");
        store.registerCustomer(newCustomer);
        store.sellProduct(p2, newCustomer, 5);
        System.out.println("Printing top 3 customers");
        System.out.println("Should be: Tom B. (14018), Frodo (8200), Fredigar (6000)");
        for(Customer c: store.getTopXCustomers(3)){
            System.out.println(c);
        }

        System.out.println("Adding more of product #1 (Desktop $1000)");
        store.addStock(p1, 10);
        store.sellProduct(p1, c2, 6);
        store.sellProduct(p1, c3, 3);
        store.sellProduct(p1, c1, 2);
        System.out.println("Printing top 4 customers");
        System.out.println("Should be: Tom B. (17018), Galadriel (10000), Frodo (8200), Fredigar (6000)");
        for(Customer c: store.getTopXCustomers(4)){
            System.out.println(c);
        }

        System.out.println("Printing out all customers");
        System.out.println("First four should match above, followed by 5 others in any order");
        for(Customer c: store.getTopXCustomers(store.getCustomers().size()+1)){
            System.out.println(c);
        }

        System.out.println("Saving store to store.dat file");
        store.saveToFile("store.dat");
    }
}
