public class LegacyOrderFacade {

    // Composition: The facade holds an instance of the legacy processor
    private final LegacyOrderProcessor legacyProcessor;

    public LegacyOrderFacade() {
        this.legacyProcessor = new LegacyOrderProcessor();
    }

    // Clean placeOrder method mapping directly to the legacy processor
    public void placeOrder(String customerEmail, String itemCode, double amount, String deliveryAddress) {
        legacyProcessor.processOrder(customerEmail, itemCode, amount, deliveryAddress);
    }
}
