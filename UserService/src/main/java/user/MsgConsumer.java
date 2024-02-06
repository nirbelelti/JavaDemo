package user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

import org.rabbitmq.RabbitMQUtils;

public class MsgConsumer {
    private final static String QUEUE_NAME = "createUserQueue";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static UserFacade facade = new UserFacade();

    public static void main(String[] args) {
        try {
            // Establish a connection to RabbitMQ server
            Connection connection = RabbitMQUtils.getConnection();

            // Create a channel
            Channel channel = connection.createChannel();

            // Declare a queue (creates the queue if not exists)
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Create a consumer and specify the callback to handle incoming messages
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");

                // Extract data from the message
                JsonNode jsonNode = objectMapper.readTree(message);
                String firstName = jsonNode.get("firstName").asText();
                String lastName = jsonNode.get("lastName").asText();
                String address = jsonNode.get("address").asText();

                // Process the data
                int userId = facade.createUser(firstName, lastName, address);
                User user = new User(userId, firstName, lastName, address);
                String responseMessage = user.toString();

                // Send the response back to the sender using the replyTo and correlationId
                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, responseMessage.getBytes(StandardCharsets.UTF_8));

                System.out.println(" [x] Response sent: '" + responseMessage + "'");
            };

            // Consume messages from the queue
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});

            // Keep the consumer running (you can add your own logic to gracefully stop the consumer)
            System.out.println(" [*] Waiting for messages. To exit press Ctrl+C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
