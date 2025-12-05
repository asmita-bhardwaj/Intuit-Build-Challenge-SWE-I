package assignment1.blockingqueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Main class sets up the Producer-Consumer simulation.
 * It creates the source and destination containers, initializes
 * the shared BlockingQueue, and starts both threads.
 */
public class BlockingQueueMain {

    // Number of items expected to be produced/consumed.
    // Consumer uses this to know when to stop.
    public static final int EXPECTED_ITEM_COUNT = 5;

    public static void main(String[] args) throws InterruptedException {

        // Source container representing input data for the Producer
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);

        // Destination container to store items consumed by the Consumer
        List<Integer> destination = new ArrayList<>();

        // Shared queue enabling thread-safe communication
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        // The queue size is intentionally small (3) to show blocking behavior

        // Create Producer and Consumer tasks
        Producer producer = new Producer(queue, source);
        Consumer consumer = new Consumer(queue, destination);

        // Wrap each in its own thread
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        // Start both threads
        producerThread.start();
        consumerThread.start();

        // Wait for both threads to finish execution
        producerThread.join();
        consumerThread.join();

        // Print final results to verify completion
        System.out.println("\nFinal Destination List (blocking queue): " + destination);
        System.out.println("BlockingQueue simulation complete.");
    }
}