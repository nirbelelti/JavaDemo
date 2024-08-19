package config.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {

    private final static String QUEUE_NAME = "hello";


    public static void main(String[] args) {
        try {
            // Establish a connection to RabbitMQ server
            Connection connection = RabbitMQUtils.getConnection();

            // Create a channel
            Channel channel = connection.createChannel();

            // Declare a queue (creates the queue if not exists)
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Message to be sent
            String message = "Hello, RabbitMQ!" + System.currentTimeMillis();

            // Publish the message to the queue
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            // Close the channel and connection
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
