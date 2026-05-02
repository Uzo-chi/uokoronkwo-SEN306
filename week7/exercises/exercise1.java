class RoomService {
    void book(String roomtype, String guest) {
    }

    boolean isAvailable(String roomtype) {
        return true;
    }
}

class PaymentService {
    boolean charge(String guest, double price) {
        return true;
    }
}

class LoyaltyPoints {
    boolean addPoints(String guest, int price) {
        return true;
    }
}

class EmailService {
    void sendConfirmation(String guest, String roomtype) {
    }
}

public class exercise1 {

    public static void main(String[] args) {
        RoomService rooms = new RoomService();
        PaymentService payment = new PaymentService();
        LoyaltyPoints loyalty = new LoyaltyPoints();
        EmailService email = new EmailService();

        String guest = "john@example.com";
        String roomType = "DELUXE";
        double price = 250.00;

        if (rooms.isAvailable(roomType)) {
            if (payment.charge(guest, price)) {
                rooms.book(roomType, guest);
                loyalty.addPoints(guest, (int) price);
                email.sendConfirmation(guest, roomType);
                System.out.println("Booking confirmed");
            } else {
                System.out.println("Payment declined");
            }
        } else {
            System.out.println("Room not available");
        }
    }
}

// Reasons this code needs a facade:
// 1. There is high coupling to subsystem classes.
// 2. The client is forced to manage complex step-by-step workflow of a hotel
// booking.
// 3. There is poor code maintainability and code duplication
