import java.util.*;

/*
 * UseCase11ConcurrentBookingSimulation
 * Demonstrates thread-safe booking allocation using synchronization.
 */

/* Booking Request */
class BookingRequest {

    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
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


/* Inventory Service (Shared Resource) */
class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    /* Critical Section */
    public synchronized void allocateRoom(BookingRequest request) {

        String roomType = request.getRoomType();

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            System.out.println(Thread.currentThread().getName()
                    + " → Booking confirmed for "
                    + request.getGuestName()
                    + " (" + roomType + ")");

        } else {

            System.out.println(Thread.currentThread().getName()
                    + " → Booking failed for "
                    + request.getGuestName()
                    + " (" + roomType + " unavailable)");
        }
    }

    public void displayInventory() {

        System.out.println("\nRemaining Inventory:");

        for (String key : inventory.keySet()) {
            System.out.println(key + " : " + inventory.get(key));
        }
    }
}


/* Shared Booking Queue */
class BookingQueue {

    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
    }

    public synchronized BookingRequest getRequest() {

        if (queue.isEmpty())
            return null;

        return queue.poll();
    }
}


/* Booking Processor Thread */
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private InventoryService inventory;

    public BookingProcessor(BookingQueue queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            BookingRequest request = queue.getRequest();

            if (request == null)
                break;

            inventory.allocateRoom(request);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        }
    }
}


/* Main Application */
public class BookMyStayAppUC11 {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println(" Book My Stay App - Version 11.1");
        System.out.println(" Concurrent Booking Simulation");
        System.out.println("====================================");

        InventoryService inventory = new InventoryService();
        BookingQueue queue = new BookingQueue();

        /* Simulated Guest Requests */
        queue.addRequest(new BookingRequest("Alice", "Single Room"));
        queue.addRequest(new BookingRequest("Bob", "Single Room"));
        queue.addRequest(new BookingRequest("Charlie", "Single Room"));
        queue.addRequest(new BookingRequest("David", "Double Room"));
        queue.addRequest(new BookingRequest("Emma", "Suite Room"));

        /* Multiple Threads Processing Bookings */
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);
        BookingProcessor t3 = new BookingProcessor(queue, inventory);

        t1.setName("Processor-1");
        t2.setName("Processor-2");
        t3.setName("Processor-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        inventory.displayInventory();
    }
}