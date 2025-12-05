package assignment1.waitnotify;

import java.util.List;

/**
 * Producer for the manual wait/notify implementation.
 * Iterates over a source list and inserts items into ManualBuffer.
 */
public class ManualProducer implements Runnable {

    private final ManualBuffer buffer;
    private final List<Integer> source;

    public ManualProducer(ManualBuffer buffer, List<Integer> source) {
        this.buffer = buffer;
        this.source = source;
    }

    @Override
    public void run() {
        try {
            for (Integer val : source) {
                buffer.put(val); // may block if buffer is full
                Thread.sleep(100); // optional: makes console output easier to read
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupt flag
        }
    }
}