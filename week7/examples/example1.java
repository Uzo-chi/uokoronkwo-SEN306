Inventory inv = new Inventory();
Payment pay = new Payment();
Shipping ship = new Shipping();
Email email = new Email();

if (inv.checkStock("LAPTOP")) {
    if (pay.charge("alice@example.com", 999.99)) {
        inv.reserve("LAPTOP");
        String label = ship.createLabel("123 Main St");
        ship.schedulePickup(label);
        email.send("alice@example.com", "Order confirmed", "Your order is on the way");
        System.out.println("Order success!");
    } else {
        System.out.println("Payment failed");
    }
} else {
    System.out.println("Out of stock");
}
