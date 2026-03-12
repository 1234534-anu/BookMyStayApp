import java.util.*;

/**
 * UseCase6RoomAllocationService
 *
 * Demonstrates reservation confirmation and safe room allocation
 * while preventing double booking using Set and HashMap.
 *
 * @author Anuja
 * @version 6.1
 */

/* Reservation Request */
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


/* Booking Request Queue */
class BookingQueue {

    private Queue<Reservation> requestQueue;

    public BookingQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        requestQueue.add(r);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }
}


/* Inventory Service */
class InventoryService {

    private HashMap<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}


/* Booking Service for Allocation */
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> roomAllocations = new HashMap<>();
    private int roomCounter = 1;

    public void processBooking(Reservation reservation, InventoryService inventory) {

        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("Booking failed for " + reservation.getGuestName() +
                    " (No rooms available)");
            return;
        }

        String roomId = generateRoomId(roomType);

        allocatedRoomIds.add(roomId);

        roomAllocations.putIfAbsent(roomType, new HashSet<>());
        roomAllocations.get(roomType).add(roomId);

        inventory.decrementRoom(roomType);

        System.out.println("Reservation Confirmed!");
        System.out.println("Guest : " + reservation.getGuestName());
        System.out.println("Room Type : " + roomType);
        System.out.println("Assigned Room ID : " + roomId);
        System.out.println("--------------------------------");
    }

    private String generateRoomId(String roomType) {

        String prefix = roomType.replace(" ", "").substring(0, 2).toUpperCase();
        String roomId = prefix + roomCounter++;

        while (allocatedRoomIds.contains(roomId)) {
            roomId = prefix + roomCounter++;
        }

        return roomId;
    }
}


/* Main Application */
public class BookMyStayAppUC6 {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("   Book My Stay App - Version 6.1");
        System.out.println("====================================");

        BookingQueue bookingQueue = new BookingQueue();
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService();

        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingQueue.addRequest(new Reservation("David", "Single Room"));

        while (!bookingQueue.isEmpty()) {
            Reservation r = bookingQueue.getNextRequest();
            bookingService.processBooking(r, inventory);
        }

        System.out.println("All booking requests processed.");
    }
}