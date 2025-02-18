import java.util.concurrent.locks.ReentrantLock;
class TicketBooking {
    private static int availableSeats = 10;
    private static final ReentrantLock lock = new ReentrantLock();
    public void bookTicket(String customerName, boolean isVIP) {
        lock.lock();  // Acquire lock to ensure synchronized access to availableSeats
        try {
            if (availableSeats > 0) {
                if (isVIP) {
                    System.out.println(customerName + " (VIP) is booking a seat.");
                } else {
                    System.out.println(customerName + " is booking a seat.");
                }
		try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                availableSeats--;
                System.out.println("Booking successful for " + customerName + ". Seats remaining: " + availableSeats);
            } else {
                System.out.println("No available seats for " + customerName);
            }
        } finally {
            lock.unlock();
        }
    }
    public int getAvailableSeats() {
        return availableSeats;
    }
}
class CustomerThread extends Thread {
    private TicketBooking ticketBooking;
    private String customerName;
    private boolean isVIP;
    public CustomerThread(TicketBooking ticketBooking, String customerName, boolean isVIP) {
        this.ticketBooking = ticketBooking;
        this.customerName = customerName;
        this.isVIP = isVIP;
    }
    public void run() {
        ticketBooking.bookTicket(customerName, isVIP);
    }
}
public class TicketBookingSystem {
    public static void main(String[] args) {
        TicketBooking ticketBooking = new TicketBooking();

        // Creating threads for customers (VIP and regular)
        CustomerThread customer1 = new CustomerThread(ticketBooking, "Alice", true);
        CustomerThread customer2 = new CustomerThread(ticketBooking, "Bob", false);
        CustomerThread customer3 = new CustomerThread(ticketBooking, "Charlie", false);
        CustomerThread customer4 = new CustomerThread(ticketBooking, "David", true)
        CustomerThread customer5 = new CustomerThread(ticketBooking, "Eve", false);
        CustomerThread customer6 = new CustomerThread(ticketBooking, "Frank", true);

        customer1.setPriority(Thread.MAX_PRIORITY);
        customer2.setPriority(Thread.NORM_PRIORITY);
        customer3.setPriority(Thread.NORM_PRIORITY);
        customer4.setPriority(Thread.MAX_PRIORITY);
        customer5.setPriority(Thread.NORM_PRIORITY);
        customer6.setPriority(Thread.MAX_PRIORITY);
        customer1.start();
        customer2.start();
        customer3.start();
        customer4.start();
        customer5.start();
        customer6.start();
        try {
            customer1.join();
            customer2.join();
            customer3.join();
            customer4.join();
            customer5.join();
            customer6.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("\nAll booking attempts completed. Remaining seats: " + ticketBooking.getAvailableSeats());
    }
}
