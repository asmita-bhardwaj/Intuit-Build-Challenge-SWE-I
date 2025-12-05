package assignment1.blockingqueue;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Consumer retrieves items from a shared BlockingQueue and stores them
 * in a destination container. This demonstrates safe removal and proper
 * synchronization with the Producer.
 */
public class Consumer implements Runnable {

    private final BlockingQueue<Integer> queue;
    private final List<Integer> destination;

    /**
     * @param queue        The shared BlockingQueue from which items are consumed.
     * @param destination  The list where consumed items will be stored.
     */
    public Consumer(BlockingQueue<Integer> queue, List<Integer> destination) {
        this.queue = queue;
        this.destination = destination;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Take blocks if queue is empty — no need for manual wait/notify
                Integer item = queue.take();
                destination.add(item);
                System.out.println("Consumed (blocking queue): " + item);

                // Break condition: stop when we've consumed everything produced
                // We stop when destination size matches expected items
                if (destination.size() == BlockingQueueMain.EXPECTED_ITEM_COUNT) {
                    break;
                }

                Thread.sleep(100); // Optional delay for cleaner logging
            }
        } catch (InterruptedException e) {
            // Restore interrupt flag and exit cleanly
            Thread.currentThread().interrupt();
        }
    }
}