import java.util.*;

/**
 * UseCase9ErrorHandlingValidation
 *
 * Demonstrates validation and error handling using
 * custom exceptions to prevent invalid booking scenarios.
 *
 * @author Anuja
 * @version 9.1
 */

/* Custom Exception */
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}


/* Reservation Class */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


/* Inventory Service */
class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}


/* Booking Validator */
class InvalidBookingValidator {

    public static void validateReservation(Reservation reservation,
                                           InventoryService inventory)
            throws InvalidBookingException {

        if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isValidRoomType(reservation.getRoomType())) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (inventory.getAvailability(reservation.getRoomType()) <= 0) {
            throw new InvalidBookingException("No rooms available for selected type.");
        }
    }
}


/* Main Application */
public class BookMyStayAppUC9 {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("   Book My Stay App - Version 9.1");
        System.out.println("====================================");

        InventoryService inventory = new InventoryService();

        List<Reservation> requests = new ArrayList<>();

        // Example requests (some invalid)
        requests.add(new Reservation("Alice", "Single Room"));
        requests.add(new Reservation("Bob", "Luxury Room")); // invalid room type
        requests.add(new Reservation("", "Double Room")); // invalid guest name
        requests.add(new Reservation("Charlie", "Suite Room"));

        for (Reservation r : requests) {

            try {

                InvalidBookingValidator.validateReservation(r, inventory);

                inventory.decrementRoom(r.getRoomType());

                System.out.println("Booking confirmed for "
                        + r.getGuestName()
                        + " (" + r.getRoomType() + ")");

            } catch (InvalidBookingException e) {

                System.out.println("Booking failed: " + e.getMessage());

            }
        }

        System.out.println("\nSystem continues running safely.");
    }
}