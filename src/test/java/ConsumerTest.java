import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConsumerTest {
    private MessageQueue messageQueue;

    @BeforeEach
    void setUp() {
        messageQueue = new MessageQueue();
        Consumer.resetCounters(); // Reset counts before each test
    }

    @Test
    void testConsumerProcessesSuccessMessage() throws InterruptedException {
        messageQueue.produce("Message Success");
        Consumer consumer = new Consumer(messageQueue, 1);
        Thread consumerThread = new Thread(consumer);

        consumerThread.start();
        consumerThread.join();

        // Assert - verify success count and currentMessageCount
        assertEquals(1, Consumer.getSuccessCount(), "Success count should be incremented.");
        assertEquals(0, Consumer.getErrorCount(), "Error count should remain zero.");
    }

    @Test
    void testConsumerProcessesErrorMessage() throws InterruptedException {
        messageQueue.produce("Message Error");
        Consumer consumer = new Consumer(messageQueue, 1);
        Thread consumerThread = new Thread(consumer);

        consumerThread.start();
        consumerThread.join();

        // Assert - verify error count and currentMessageCount
        assertEquals(0, Consumer.getSuccessCount(), "Success count should remain zero.");
        assertEquals(1, Consumer.getErrorCount(), "Error count should be incremented.");
    }

    @Test
    void testConsumerStopsAfterMaxMessageCount() throws InterruptedException {
        // Produce multiple messages
        messageQueue.produce("Message 1 Success");
        messageQueue.produce("Message 2 Success");
        messageQueue.produce("Message 3 Error");

        // Consumer should process only up to maxMessageCount
        int maxMessageCount = 2;
        Consumer consumer = new Consumer(messageQueue, maxMessageCount);
        Thread consumerThread = new Thread(consumer);

        consumerThread.start();
        consumerThread.join();

        // Assert - consumer should only process maxMessageCount messages
        assertEquals(2, consumer.getCurrentMessageCount(), "Consumer should stop after processing maxMessageCount messages.");
        assertEquals(2, Consumer.getSuccessCount(), "One success message should be processed.");
        assertEquals(0, Consumer.getErrorCount(), "One error message should be processed.");
    }

    @Test
    void testConsumerTimeout() throws InterruptedException {
        // Don't produce any message to test timeout
        Consumer consumer = new Consumer(messageQueue, 1);
        Thread consumerThread = new Thread(consumer);

        consumerThread.start();
        consumerThread.join();

        // Assert - consumer should timeout and not process any message
        assertEquals(0, Consumer.getSuccessCount(), "Success count should remain zero when no message is produced.");
        assertEquals(0, Consumer.getErrorCount(), "Error count should remain zero when no message is produced.");
    }
}
