package assignment1.blockingqueue;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Producer reads items from a source container and places them
 * into a shared BlockingQueue. This demonstrates thread-safe
 * communication between producer and consumer.
 */
public class Producer implements Runnable {

    private final BlockingQueue<Integer> queue;
    private final List<Integer> source;

    /**
     * @param queue  The shared BlockingQueue used for thread-safe transfer.
     * @param source The list of items the Producer will read from.
     */
    public Producer(BlockingQueue<Integer> queue, List<Integer> source) {
        this.queue = queue;
        this.source = source;
    }

    @Override
    public void run() {
        try {
            for (Integer item : source) {
                queue.put(item);  // BlockingQueue handles waiting if the queue is full
                System.out.println("Produced (blocking queue): " + item);

                // Optional small delay for clearer logs, not required for correctness
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            // If the thread is interrupted, restore interrupt flag and exit gracefully
            Thread.currentThread().interrupt();
        }
    }
}