import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class Product{
    abstract float getPrice();
    abstract String getName();
}

abstract class Payment {
    abstract float checkOut(Product product);
}

class OrangeJuice extends Product {
    private float price = (float) 5.0;
    private String name = "Orange Juice!";

    @java.lang.Override
    public float getPrice() {
        return this.price;
    }

    @java.lang.Override
    public String getName() {
        return this.name;
    }
}

class Biscuit extends Product {
    private float price = (float) 10.0;
    private String name = "Oreo Vanilla!";

    @java.lang.Override
    public float getPrice() {
        return this.price;
    }

    @java.lang.Override
    public String getName() {
        return this.name;
    }
}

class CreditCardPayment extends Payment {
    @java.lang.Override
    float checkOut(Product product) {
        return product.getPrice() * (float) 2;
    }
}

class CashPayment extends Payment {
    @java.lang.Override
    float checkOut(Product product) {
        return product.getPrice();
    }
}

public class VendingMachine {

    Map<String, Product> slots = new HashMap<String, Product>();
    private int capacity;

    VendingMachine(int capacity) {
        this.capacity = capacity;
    }

    public boolean addProduct(String productId, Product product) {

        if (this.capacity <= slots.size()) {
            System.out.println("I am Overloaded!");
            return false;
        }

        slots.put(productId, product);
        return true;
    }

    public float checkout(List<Product> cart, Payment payment) {
        float totalAmout = 0;
        for(Product product: cart) {
            totalAmout += payment.checkOut(product);
        }
        return totalAmout;
    }

    public boolean isInStock(String productId) {
        return this.slots.containsKey(productId);
    }

    public Product getProductById(String productId) {
        return this.slots.get(productId);
    }
}


class Customer {
    List<Product> cart = new ArrayList<Product>();

    public boolean select(String productId, VendingMachine vendingMachine) {
        if (vendingMachine.isInStock(productId)) {
            cart.add(vendingMachine.getProductById(productId));
            return true;
        }
        return false;
    }

    public float checkout(Payment payment, VendingMachine vendingMachine) {
        return vendingMachine.checkout(cart, payment);
    }
}

class Operator {
    public static void main(String[] args) {
        // Initializing Products
        OrangeJuice j1 = new OrangeJuice();
        OrangeJuice j2 = new OrangeJuice();
        Biscuit b1 = new Biscuit();

        // Preparing Vending Machine with the items.
        VendingMachine vm = new VendingMachine(5);
        vm.addProduct("p1", j1);
        vm.addProduct("p2", b1);
        vm.addProduct("p3", j2);

        // Customer is bying this two Items for example.
        Customer customer = new Customer();
        customer.select("p1", vm);
        customer.select("p3", vm);

        // Customer choosing pay by card.
        Payment card = new CreditCardPayment();

        // Customer checkout Process to get the total amount.
        float totalAmout = customer.checkout(card, vm);
        System.out.println("Total Amount to Checkout: " + totalAmout);
    }
}