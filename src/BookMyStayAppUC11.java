import java.util.*;

/**
 * UseCase10BookingCancellation
 *
 * Demonstrates booking cancellation with inventory rollback
 * using Stack to track released room IDs.
 *
 * @author Anuja
 * @version 10.1
 */

/* Reservation Class */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean cancelled;

    public Reservation(String reservationId, String guestName,
                       String roomType, String roomId) {

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.cancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }

    public void displayReservation() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("Room ID        : " + roomId);
        System.out.println("Status         : " + (cancelled ? "Cancelled" : "Confirmed"));
        System.out.println("-----------------------------");
    }
}


/* Inventory Service */
class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 1);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (String key : inventory.keySet()) {
            System.out.println(key + " : " + inventory.get(key));
        }
    }
}


/* Booking History */
class BookingHistory {

    private Map<String, Reservation> reservations;

    public BookingHistory() {
        reservations = new HashMap<>();
    }

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }

    public void displayAll() {

        System.out.println("\nBooking History:");

        for (Reservation r : reservations.values()) {
            r.displayReservation();
        }
    }
}


/* Cancellation Service */
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelReservation(String reservationId,
                                  BookingHistory history,
                                  InventoryService inventory) {

        Reservation r = history.getReservation(reservationId);

        if (r == null) {
            System.out.println("Cancellation failed: Reservation not found.");
            return;
        }

        if (r.isCancelled()) {
            System.out.println("Cancellation failed: Reservation already cancelled.");
            return;
        }

        rollbackStack.push(r.getRoomId());

        inventory.incrementRoom(r.getRoomType());

        r.cancel();

        System.out.println("Reservation " + reservationId + " cancelled successfully.");
        System.out.println("Released Room ID: " + rollbackStack.peek());
    }
}


/* Main Application */
public class BookMyStayAppUC10 {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("   Book My Stay App - Version 10.1");
        System.out.println("====================================");

        InventoryService inventory = new InventoryService();
        BookingHistory history = new BookingHistory();
        CancellationService cancellationService = new CancellationService();

        Reservation r1 = new Reservation("RES101", "Alice", "Single Room", "SR1");
        Reservation r2 = new Reservation("RES102", "Bob", "Double Room", "DR1");

        history.addReservation(r1);
        history.addReservation(r2);

        history.displayAll();

        cancellationService.cancelReservation("RES101", history, inventory);

        history.displayAll();

        inventory.displayInventory();
    }
}