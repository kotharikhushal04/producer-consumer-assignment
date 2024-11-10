import java.util.logging.Logger;

public class Producer {
    private final MessageQueue messageQueue;
    private static final Logger LOGGER = Logger.getLogger(Consumer.class.getName());

    public Producer(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    // Produces and adds a series of messages to the message queue
    public void messageProducer(){
        messageQueue.produce("Message 1 Success");
        messageQueue.produce("Message 2 Success");
        messageQueue.produce("Message 3 Success");
        messageQueue.produce("Message 1 Error");
        messageQueue.produce("Message 4 Success");
        messageQueue.produce("Message 2 Error");
        messageQueue.produce("Message 5 Success");
        LOGGER.info("Producer has finished adding messages to the queue.");
    }
}
