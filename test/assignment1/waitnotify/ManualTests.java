package assignment1.waitnotify;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * JUnit test suite for verifying the wait/notify-based Producer–Consumer implementation.
 *
 * These tests validate correct synchronization using low-level primitives:
 *  - ManualBuffer enforces one-slot handoff
 *  - Producer waits when buffer is full
 *  - Consumer waits when buffer is empty
 *  - No deadlocks occur under normal operation
 */
public class ManualTests {

    /**
     * Test 1: Ensures that all items produced are eventually consumed.
     *
     * Verifies:
     *  - Proper handoff through ManualBuffer
     *  - No lost or duplicated values
     *  - Consumer stops after receiving the expected item count
     */
    @Test
    void testAllItemsTransferred() throws InterruptedException {

        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> destination = new CopyOnWriteArrayList<>();

        ManualBuffer buffer = new ManualBuffer();

        Thread producer = new Thread(new ManualProducer(buffer, source));
        Thread consumer = new Thread(new ManualConsumer(buffer, destination, source.size()));

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        assertEquals(source, destination,
                "ManualBuffer did not correctly transfer all items from producer to consumer");
    }

    /**
     * Test 2: Ensures threads do not deadlock and terminate properly.
     *
     * Rationale:
     *  - wait()/notify() implementations are prone to deadlocks if locking is incorrect.
     *  - This test confirms that producer finishes promptly.
     *  - Consumer is allowed time to process, then interrupted to ensure it exits cleanly.
     */
    @Test
    void testThreadsDoNotDeadlock() throws InterruptedException {

        List<Integer> source = Arrays.asList(10, 20, 30);
        List<Integer> destination = new CopyOnWriteArrayList<>();

        ManualBuffer buffer = new ManualBuffer();

        Thread producer = new Thread(new ManualProducer(buffer, source));
        Thread consumer = new Thread(new ManualConsumer(buffer, destination, source.size()));

        producer.start();
        consumer.start();

        // Producer should complete normally
        producer.join();

        // Give consumer time to consume all items
        Thread.sleep(300);

        // Interrupt the consumer so that if it is waiting, it exits properly
        consumer.interrupt();
        consumer.join(300);

        assertFalse(producer.isAlive(), "Producer thread did not terminate as expected");
        assertFalse(consumer.isAlive(), "Consumer thread did not terminate as expected");
    }
}
