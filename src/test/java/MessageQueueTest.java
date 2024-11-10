import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageQueueTest {
    private MessageQueue messageQueue;

    @BeforeEach
    void setUp() {
        messageQueue = new MessageQueue();
    }

    @Test
    void testProduceAndConsumeMessage() throws InterruptedException {
        // Test that a produced message can be consumed
        messageQueue.produce("Test Message");
        String message = messageQueue.consume();
        assertEquals("Test Message", message, "The consumed message should match the produced message.");
    }

    @Test
    void testConsumeTimeout() throws InterruptedException {
        // Test that consume returns null if no message is produced within 3 seconds
        String message = messageQueue.consume();
        assertNull(message, "Consumer should return null when no message is available within the timeout.");
    }


}
