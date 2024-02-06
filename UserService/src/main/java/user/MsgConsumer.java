package user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import org.rabbitmq.RabbitMQUtils;


public class MsgConsumer {
    private final static String QUEUE_NAME = "createUserQueue";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static UserFacade facade = new UserFacade();


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
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received here '" + message + "'");
               //User user = objectMapper.readValue(delivery.getBody(), User.class);
                //System.out.println("Received user details: " + user.getFirstName() + " " + user.getLastName() + ", Address: " + user.getAddress());


                JsonNode jsonNode = objectMapper.readTree(message);
                facade.createUser(jsonNode.get("firstName").asText(), jsonNode.get("lastName").asText(), jsonNode.get("address").asText());

                System.out.println(" [x] Received dddddd '" + message + "'");
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
