package assignment1.waitnotify;

import java.util.List;

/**
 * Consumer for the manual wait/notify version.
 * Retrieves values from ManualBuffer and stores them into a list.
 */
public class ManualConsumer implements Runnable {

    private final ManualBuffer buffer;
    private final List<Integer> destination;
    private final int expectedCount;

    public ManualConsumer(ManualBuffer buffer, List<Integer> destination, int expectedCount) {
        this.buffer = buffer;
        this.destination = destination;
        this.expectedCount = expectedCount;
    }

    @Override
    public void run() {
        try {
            // Consume until we've received the expected number of items
            while (destination.size() < expectedCount) {
                Integer val = buffer.take(); // may block if buffer is empty
                destination.add(val);
                Thread.sleep(100); // for cleaner logging
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}