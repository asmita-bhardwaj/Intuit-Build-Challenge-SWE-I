package assignment1.waitnotify;

/**
 * A simple one-slot buffer using classic wait/notify synchronization.
 * 
 * This class demonstrates low-level thread communication without
 * using higher-level utilities like BlockingQueue.
 */
public class ManualBuffer {

    private Integer item = null; // holds a single item at a time

    /**
     * Inserts a value into the buffer.
     * If the buffer is full (item != null), the producer waits.
     */
    public synchronized void put(Integer value) throws InterruptedException {
        // Wait until buffer is empty
        while (item != null) {
            wait(); // releases lock and waits
        }

        item = value;
        System.out.println("Produced (wait/notify): " + value);

        // Notify any waiting consumer that data is available
        notifyAll();
    }

    /**
     * Retrieves the current value from the buffer.
     * If the buffer is empty (item == null), the consumer waits.
     */
    public synchronized Integer take() throws InterruptedException {
        // Wait until buffer has an item
        while (item == null) {
            wait();
        }

        Integer value = item;
        item = null;

        System.out.println("Consumed (wait/notify): " + value);

        // Notify producer that buffer is now empty
        notifyAll();
        return value;
    }
}