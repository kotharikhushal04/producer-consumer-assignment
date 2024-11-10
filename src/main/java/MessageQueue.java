import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

public class MessageQueue {
    private static final Logger LOGGER = Logger.getLogger(Consumer.class.getName());
    private final Queue<String> queue = new LinkedList<String>();

    // Adds a message to the queue and notifies any waiting consumers
    public synchronized void produce(String message) {
        queue.add(message);
        notify(); // Notify waiting consumer that a new message is available
    }

    // Consumes a message from the queue with a 3-second timeout
    public synchronized String consume() throws InterruptedException {
        while (queue.isEmpty()) {
            LOGGER.info("Queue is empty, consumer waiting for a message...");
            wait(3000); // Wait for up to 3 seconds for a message to be produced
            if (queue.isEmpty()) {
                LOGGER.warning("No message available after 3 seconds. Consumer will stop waiting.");
                return null; // Return null if no message was produced within 3 seconds
            }
        }
        return queue.poll();
    }
}
