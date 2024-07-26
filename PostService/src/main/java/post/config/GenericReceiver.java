package post.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

public class GenericReceiver {

    private final String queueName;

    public GenericReceiver(String queueName ) {
        this.queueName = queueName;
    }

    String receivedMessage;

    public void start() {
        try {
            // Establish a connection to RabbitMQ server
            Connection connection = RabbitMQUtils.getConnection();

            // Create a channel
            Channel channel = connection.createChannel();

            // Declare the queue
            channel.queueDeclare(queueName, false, false, false, null);

            // Create a consumer and specify the callback to handle incoming messages
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                // Process the received message
                System.out.println("Received message from " + queueName + ": " + message);
                receivedMessage = message;
            };

            // Consume messages from the queue
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }

    public static void main(String[] args) {
        // Create instances of GenericReceiver for different queues
       // GenericReceiver userReceiver = new GenericReceiver("user-queue");
        GenericReceiver postReceiver = new GenericReceiver("post-queue");
        //GenericReceiver commentReceiver = new GenericReceiver("comment-queue");

        // Start receivers
        //userReceiver.start();
        postReceiver.start();
        //commentReceiver.start();
    }
}

