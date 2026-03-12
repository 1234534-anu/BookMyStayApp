import java.util.HashMap;
import java.util.Map;

/**
 * UseCase4RoomSearch
 *
 * Demonstrates read-only room search using centralized inventory.
 * Only room types with availability > 0 are displayed.
 *
 * @author Anuja
 * @version 4.1
 */

/* -------- Domain Model -------- */
abstract class Room {
    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq.ft");
        System.out.println("Price     : $" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 200, 100.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 350, 180.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 500, 350.0);
    }
}

/* -------- Inventory (State Holder) -------- */
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 0); // example: unavailable
        inventory.put("Suite Room", 3);
    }

    // Read-only access for search
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Optional: display current inventory (debug/visibility)
    public void displayInventory() {
        System.out.println("\n--- Inventory Snapshot ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

/* -------- Search Service (Read-Only Logic) -------- */
class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {
        System.out.println("\n--- Available Rooms ---");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check: show only rooms with availability > 0
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available : " + available);
                System.out.println("------------------------------");
            }
        }
    }
}

/* -------- Main Application -------- */
public class BookMyStayAppUC4 {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("   Book My Stay App - Version 4.1");
        System.out.println("====================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize room domain objects
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Perform read-only search
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("\nSearch completed. Inventory state unchanged.");
    }
}