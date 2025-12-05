package assignment1.waitnotify;

/**
 * Driver class for demonstrating the wait/notify producer–consumer pattern.
 *
 * This simulation shows low-level thread synchronization using:
 * - synchronized methods
 * - wait() for blocking
 * - notifyAll() for signaling
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManualMain {

    // Number of items expected so the consumer knows when to stop
    public static final int EXPECTED_COUNT = 5;

    public static void main(String[] args) throws InterruptedException {

        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> destination = new ArrayList<>();

        ManualBuffer buffer = new ManualBuffer();

        Thread producerThread = new Thread(new ManualProducer(buffer, source));
        Thread consumerThread = new Thread(new ManualConsumer(buffer, destination, EXPECTED_COUNT));

        // Start producer and consumer threads
        producerThread.start();
        consumerThread.start();

        // Wait for them to finish
        producerThread.join();
        consumerThread.join();

        // Print results for verification
        System.out.println("\nFinal Destination List (wait/notify): " + destination);
        System.out.println("Manual wait/notify simulation complete.");
    }
}