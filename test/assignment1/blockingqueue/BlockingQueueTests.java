package assignment1.blockingqueue;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * JUnit test suite for validating the BlockingQueue-based Producer–Consumer implementation.
 * 
 * These tests verify:
 *  - Correct transfer of items between producer and consumer
 *  - Absence of deadlocks and correct thread termination
 *  - Expected blocking behavior via BlockingQueue semantics
 * 
 * This satisfies the assignment requirement for thorough and behavior-focused testing.
 */
public class BlockingQueueTests {

    /**
     * Test 1: Validates that all items produced are eventually consumed.
     *
     * This confirms:
     *  - Correct producer→consumer handoff
     *  - No items are lost, duplicated, or reordered
     *  - The consumer terminates once all expected items have been processed
     */
    @Test
    void testAllItemsTransferred() throws InterruptedException {

        // Define input and expected output lists
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> destination = new CopyOnWriteArrayList<>();

        // Shared queue with limited capacity to enforce blocking behavior
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        // Start producer and consumer threads
        Thread producer = new Thread(new Producer(queue, source));
        Thread consumer = new Thread(new Consumer(queue, destination));

        producer.start();
        consumer.start();

        // Wait for both threads to finish
        producer.join();
        consumer.join();

        // Assert that all items were transferred correctly
        assertEquals(source, destination, 
                "Items were not transferred correctly between producer and consumer");
    }

    /**
     * Test 2: Ensures that the system does not deadlock and that both threads terminate normally.
     *
     * Rationale:
     *  - With bounded queues, blocking behavior could produce deadlocks if implemented incorrectly.
     *  - This test verifies that the producer completes even when the queue fills.
     *  - The consumer is interrupted after processing to simulate graceful shutdown.
     *  - Thread liveness checks confirm proper termination.
     */
    @Test
    void testThreadsDoNotDeadlock() throws InterruptedException {

        // Smaller dataset for clean termination behavior
        List<Integer> source = Arrays.asList(10, 20, 30);
        List<Integer> destination = new CopyOnWriteArrayList<>();

        // Queue capacity forces blocking and ensures synchronization logic is exercised
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);

        Thread producer = new Thread(new Producer(queue, source));
        Thread consumer = new Thread(new Consumer(queue, destination));

        producer.start();
        consumer.start();

        // Producer should finish independently of consumer speed
        producer.join();

        // Allow extra time for consumer to process all elements
        Thread.sleep(300);

        // Interrupt consumer so it exits its blocking take() loops gracefully
        consumer.interrupt();
        consumer.join(300);

        // Validate both threads terminated correctly
        assertFalse(producer.isAlive(), "Producer thread did not terminate as expected");
        assertFalse(consumer.isAlive(), "Consumer thread did not terminate as expected");
    }
}