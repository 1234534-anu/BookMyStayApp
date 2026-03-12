import java.util.*;

/**
 * UseCase7AddOnServiceSelection
 *
 * Demonstrates attaching optional services to an existing reservation
 * without modifying booking or inventory logic.
 *
 * @author Anuja
 * @version 7.1
 */

/* Add-On Service Class */
class Service {

    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void displayService() {
        System.out.println(serviceName + " - $" + price);
    }
}


/* Add-On Service Manager */
class AddOnServiceManager {

    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, Service service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());

        reservationServices.get(reservationId).add(service);

        System.out.println("Service added to Reservation " + reservationId +
                ": " + service.getServiceName());
    }

    // Display services for reservation
    public void displayServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId);

        for (Service s : services) {
            s.displayService();
        }
    }

    // Calculate total additional cost
    public double calculateTotalServiceCost(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        double total = 0;

        if (services != null) {
            for (Service s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }
}


/* Main Application */
public class BookMyStayAppUC7 {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("   Book My Stay App - Version 7.1");
        System.out.println("====================================");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        String reservationId = "RES101";

        // Available services
        Service breakfast = new Service("Breakfast", 20);
        Service airportPickup = new Service("Airport Pickup", 40);
        Service spa = new Service("Spa Access", 60);

        // Guest selects services
        serviceManager.addService(reservationId, breakfast);
        serviceManager.addService(reservationId, airportPickup);
        serviceManager.addService(reservationId, spa);

        // Display selected services
        serviceManager.displayServices(reservationId);

        // Calculate total cost
        double totalCost = serviceManager.calculateTotalServiceCost(reservationId);

        System.out.println("\nTotal Additional Service Cost: $" + totalCost);

        System.out.println("\nBooking and inventory remain unchanged.");
    }
}