import java.util.logging.Logger;

public class Consumer implements Runnable{
    private final MessageQueue messageQueue;
    private final int maxMessageCount;
    private int currentMessageCount=0;
    private static int successCount = 0;
    private static int errorCount = 0;
    private static final Logger LOGGER = Logger.getLogger(Consumer.class.getName());

    public Consumer(MessageQueue messageQueue, Integer maxMessageCount) {
        this.messageQueue = messageQueue;
        this.maxMessageCount = maxMessageCount;
    }

    @Override
    public void run() {
        // Process messages until maxMessageCount is reached or the queue is empty
        while (currentMessageCount < maxMessageCount) {
            try {
                // Attempt to consume a message from the queue
                String message = messageQueue.consume();
                if (message == null) { // Exit if no message received within timeout
                    LOGGER.info("No message received within timeout. Consumer is stopping.");
                    break;
                }
                // Process the retrieved message
                processMessage(message);
                currentMessageCount++; // Count the processed message
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Gracefully handle interruption
                break;
            }
            LOGGER.info("Consumer finished processing message.");
        }
    }

    private void processMessage(String message){
        try {
            if (message.contains("Error")) {
                throw new RuntimeException("Simulated error");
            }
            LOGGER.info("Processed message successfully: " + message);
            successCount++;
        } catch (Exception e) {
            LOGGER.warning("Processed message is erroneous: " + message);
            errorCount++;
        }
    }

    public static int getSuccessCount() {
        return successCount;
    }

    public static int getErrorCount() {
        return errorCount;
    }

    public int getCurrentMessageCount(){
        return currentMessageCount;
    }

    public void stop(){

    }

    // Reset counters, for testing purposes
    public static void resetCounters() {
        successCount = 0;
        errorCount = 0;
    }


}
