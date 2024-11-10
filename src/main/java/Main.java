import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

//Run the main method of the Main class as it is the starting point of the program

public class Main {
    public static void main(String[] args) {
        // Initialize the message queue where producers and consumers will exchange messages
        MessageQueue messageQueue = new MessageQueue();

        int maxMessageCount = 5; // Set the maximum number of messages that the consumer should process

        // Instantiate the consumer with the message queue and max message limit
        Consumer consumer = new Consumer(messageQueue, maxMessageCount);
        // Run the consumer in a separate thread to allow asynchronous processing
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        // Instantiate and trigger the producer to add messages to the queue
        Producer producer = new Producer(messageQueue);
        producer.messageProducer();

        try {
            consumerThread.join(); // Wait for the consumer thread to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Total Success: " + Consumer.getSuccessCount());
        System.out.println("Total Errors: " + Consumer.getErrorCount());
    }
}
