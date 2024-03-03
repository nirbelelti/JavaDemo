package org.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//}
public class SenderWithResponse {


    private final  String QUEUE_NAME; //= "createUserQueue";
    private final  String REPLY_QUEUE_NAME; // = "userReplyQueue";

    public SenderWithResponse(String queueName, String replyQueueName    ) {
        this.QUEUE_NAME = queueName;
        this.REPLY_QUEUE_NAME = replyQueueName;
    }

    public String sendMessage(String message) {
        try {
            // Establish a connection to RabbitMQ server
            Connection connection = RabbitMQUtils.getConnection();

            // Create a channel
            Channel channel = connection.createChannel();

            // Declare a reply queue
            channel.queueDeclare(REPLY_QUEUE_NAME, false, false, false, null);

            // Generate a unique correlationId for this request
            String correlationId = UUID.randomUUID().toString();

            // Set up message properties with the replyTo and correlationId
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .replyTo(REPLY_QUEUE_NAME)
                    .correlationId(correlationId)
                    .build();

            // Publish the message to the queue with properties
            channel.basicPublish("", QUEUE_NAME, properties, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");

            // Create a blocking queue to wait for the response
            BlockingQueue<String> responseQueue = new ArrayBlockingQueue<>(1);

            // Set up a consumer for the response
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                if (correlationId.equals(delivery.getProperties().getCorrelationId())) {
                    responseQueue.offer(new String(delivery.getBody(), StandardCharsets.UTF_8));
                }
            };

            channel.basicConsume(REPLY_QUEUE_NAME, true, deliverCallback, consumerTag -> {});

            String response = responseQueue.poll(10000, java.util.concurrent.TimeUnit.MILLISECONDS);

            channel.close();
            connection.close();

            return response != null ? response : "No response received";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}