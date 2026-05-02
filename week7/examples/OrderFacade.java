public class OrderFacade {

    private Inventory inventory;
    private Payment payment;
    private Shipping shipping;
    private Email email;

    public OrderFacade() {
        this.inventory = new Inventory();
        this.payment = new Payment();
        this.shipping = new Shipping();
        this.email = new Email();
    }

    public boolean placeOrder(String userId, String productId, double price, String address) {
        if (!inventory.checkStock(productId))
            return false;
        if (!payment.charge(userId, price))
            return false;

        inventory.reserve(productId);
        String label = shipping.createLabel(address);
        shipping.schedulePickup(label);
        email.send(userId, "Order Confirmed", "On its way!");
        return true;
    }

    public static void main(String[] args) {
        OrderFacade facade = new OrderFacade();
        facade.placeOrder("alice@example.com", "LAPTOP", 999.99, "123 Main St");
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
    }
}
