import java.util.*;

/**
 * UseCase8BookingHistoryReport
 *
 * Demonstrates booking history tracking and reporting
 * using List data structure to store confirmed reservations.
 *
 * @author Anuja
 * @version 8.1
 */

/* Reservation Class */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("-------------------------------");
    }
}


/* Booking History Class */
class BookingHistory {

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Add confirmed reservation to history
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation stored in history: " + reservation.getReservationId());
    }

    // Retrieve reservation list
    public List<Reservation> getReservations() {
        return reservations;
    }
}


/* Booking Report Service */
class BookingReportService {

    public void generateReport(List<Reservation> reservations) {

        System.out.println("\n===== Booking History Report =====");

        for (Reservation r : reservations) {
            r.displayReservation();
        }

        System.out.println("Total Confirmed Bookings: " + reservations.size());
    }
}


/* Main Application */
public class BookMyStayAppUC8 {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("   Book My Stay App - Version 8.1");
        System.out.println("====================================");

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed reservations
        Reservation r1 = new Reservation("RES101", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES102", "Bob", "Double Room");
        Reservation r3 = new Reservation("RES103", "Charlie", "Suite Room");

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin generates report
        reportService.generateReport(history.getReservations());

        System.out.println("\nBooking history stored in chronological order.");
    }
}