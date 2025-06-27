package utils;

public class DisplayUtils {
    /** Affiche un message, puis fait une pause de 1000 ms */
    public static void display(String message) {
        System.out.println(message);
        try {
            Thread.sleep(1000);   // pause de 1000 ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}