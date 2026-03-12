/**
 * UseCase2RoomInitialization
 *
 * Demonstrates basic room types and static availability
 * using abstraction, inheritance and polymorphism.
 *
 * @author Anuja
 * @version 2.1
 */

/* Abstract Room Class */
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

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq.ft");
        System.out.println("Price     : $" + price);
    }
}


/* Single Room Class */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 100.0);
    }
}


/* Double Room Class */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 180.0);
    }
}


/* Suite Room Class */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 500, 350.0);
    }
}


/* Main Application Class */
public class BookMyStayAppUC2 {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("     Book My Stay App - Version 2.1");
        System.out.println("======================================");

        /* Creating Room Objects (Polymorphism) */
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        /* Static Availability Variables */
        int singleRoomAvailability = 10;
        int doubleRoomAvailability = 6;
        int suiteRoomAvailability = 3;

        System.out.println("\n--- Room Details ---\n");

        single.displayRoomDetails();
        System.out.println("Availability : " + singleRoomAvailability);
        System.out.println("--------------------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Availability : " + doubleRoomAvailability);
        System.out.println("--------------------------------------");

        suite.displayRoomDetails();
        System.out.println("Availability : " + suiteRoomAvailability);
        System.out.println("--------------------------------------");

        System.out.println("\nThank you for using Book My Stay App!");
    }
}