package org.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class UserMsgSender {

    private final static String QUEUE_NAME = "createUserQueue";


    public String creatUser(String message) {
        try {
            // Establish a connection to RabbitMQ server
            Connection connection = RabbitMQUtils.getConnection();

            // Create a channel
            Channel channel = connection.createChannel();

            // Declare a queue (creates the queue if not exists)
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Publish the message to the queue
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            // Close the channel and connection
            channel.close();
            connection.close();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
