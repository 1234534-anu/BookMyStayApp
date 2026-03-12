import java.util.LinkedList;
import java.util.Queue;

/**
 * UseCase5BookingRequestQueue
 *
 * Demonstrates booking request intake using FIFO queue.
 * Booking requests are stored in arrival order and
 * no inventory mutation occurs at this stage.
 *
 * @author Anuja
 * @version 5.1
 */

/* Reservation class representing guest booking request */
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

    public void displayReservation() {
        System.out.println("Guest : " + guestName + " | Requested Room : " + roomType);
    }
}


/* Booking Queue Manager */
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // Display queue requests
    public void displayQueue() {
        System.out.println("\n--- Booking Request Queue (FIFO Order) ---");

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }
}


/* Main Application Class */
public class BookMyStayAppUC5 {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("   Book My Stay App - Version 5.1");
        System.out.println("====================================");

        // Initialize queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queue
        bookingQueue.displayQueue();

        System.out.println("\nAll booking requests stored in FIFO order.");
        System.out.println("No room allocation performed at this stage.");
    }
}