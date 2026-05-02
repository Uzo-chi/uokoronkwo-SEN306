import java.time.LocalTime;

public class CheckoutFacade {
    private Inventory inventory;
    private Payment payment;
    private Shipping shipping;
    private Email email;

    public CheckoutFacade() {
        this.inventory = new Inventory();
        this.payment = new Payment();
        this.shipping = new Shipping();
        this.email = new Email();
    }

    public OrderResult checkout(String userId, String productId, double price, String address) {
        Logger logger = new Logger();
        if (!payment.charge(userId, price)) {
            OrderResult failedOrder = new OrderResult(false, "N/A", "Failed to order!");

            logger.log(LocalTime.now(), userId, false);

            return failedOrder;
        }
        if (!shipping.isAvailable()) {
            payment.refund(userId, price);
            inventory.release(productId);

            logger.log(LocalTime.now(), userId, false);

            return new OrderResult(false, "N/A", "Failed to order!");
        }
        TaxCalculator tax = new TaxCalculator();
        String subject = "---Checkout receipt---";
        String body = "Total price: $" + price + " + " + tax.computeTax("CA", price);

        email.send(userId, subject, body);

        logger.log(LocalTime.now(), userId, true);

        return new OrderResult(true, shipping.createLabel(address), "Order successfully placed!");
    }
}

class Logger {
    public void log(LocalTime time, String userId, boolean success) {
        System.out.println("Time: " + time + "\nUser: " + userId + "\nSuccess: " + success);
    }
}

class Inventory {
    boolean checkStock(String productId) {
        return true;
    }

    void reserve(String productId) {
        System.out.println("Reserved " + productId);
    }

    void release(String productId) {
        System.out.println("Released " + productId);
    }
}

class Payment {
    boolean charge(String userId, double price) {
        return true;
    }

    void refund(String userId, double amount) {
        System.out.println("Refunded " + amount);
    }
}

class Shipping {
    void schedulePickup(String label) {
        System.out.println("Pickup scheduled for " + label);
    }

    String createLabel(String address) {
        return "TRX" + System.currentTimeMillis();
    }

    boolean isAvailable() {
        return true;
    }
}

class Email {
    void send(String to, String subject, String body) {
        System.out.println("Email to " + to);
        System.out.println(subject + "\n\n" + body);
    }
}

class TaxCalculator {
    public double computeTax(String userState, double amount) {
        if (userState == "CA") {
            return amount * 0.08;
        }
        return 0;
    }
}

class OrderResult {
    private final boolean success;
    private final String trackingNumber;
    private final String message;

    public OrderResult(boolean success, String trackNum, String message) {
        this.success = success;
        this.trackingNumber = trackNum;
        this.message = message;
    }

    public String getTrackingNumber() {
        return this.trackingNumber;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean getOrderSuccess() {
        return this.success;
    }
}
