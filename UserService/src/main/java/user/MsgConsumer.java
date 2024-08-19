package user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

import user.config.RabbitMQUtils;

public class MsgConsumer {
    private final static String CREATE_USER_QUEUE = "createUserQueue";
    private final static String GET_USER_QUEUE = "getUserQueue";
    private final static String GET_ALL_USERS_QUEUE = "getAllUsersQueue";
    private final static String UPDATE_USER_QUEUE = "updateUserQueue";
    private final static String DELETE_USER_QUEUE = "deleteUserQueue";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final UserFacade facade = new UserFacade();


    public void createUserStart() {
        try {
            // Establish a connection to RabbitMQ server
            Connection connection = RabbitMQUtils.getConnection();

            // Create a channel
            Channel channel = connection.createChannel();

            // Declare a queue (creates the queue if not exists)
            channel.queueDeclare(CREATE_USER_QUEUE, false, false, false, null);

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
            channel.basicConsume(CREATE_USER_QUEUE, true, deliverCallback, consumerTag -> {
            });

            // Keep the consumer running (you can add your own logic to gracefully stop the consumer)
            System.out.println(" [*] Waiting for messages. To exit press Ctrl+C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllUsersStart() {
        try {
            Connection connection = RabbitMQUtils.getConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(GET_ALL_USERS_QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String users = String.valueOf(facade.allUsers());

                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, users.getBytes(StandardCharsets.UTF_8));
            };
            channel.basicConsume(GET_ALL_USERS_QUEUE, true, deliverCallback, consumerTag -> {
            });

            System.out.println(" [*] Waiting for messages. To exit press Ctrl+C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUserStart() {
        try {
            Connection connection = RabbitMQUtils.getConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(GET_USER_QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

                JsonNode jsonNode = objectMapper.readTree(message);
                int id = jsonNode.get("id").asInt();

               User user = facade.getUser(id);
               String resault = user.toString();

                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, resault.getBytes(StandardCharsets.UTF_8));
            };

            channel.basicConsume(GET_USER_QUEUE, true, deliverCallback, consumerTag -> {
            });

            System.out.println(" [*] Waiting for messages...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserStart() {
        try {
            Connection connection = RabbitMQUtils.getConnection();

            Channel channel = connection.createChannel();

            channel.queueDeclare(UPDATE_USER_QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
                JsonNode jsonNode = objectMapper.readTree(message);
                int id = jsonNode.get("id").asInt();
                String firstName = jsonNode.get("firstName").asText();
                String lastName = jsonNode.get("lastName").asText();
                String address = jsonNode.get("address").asText();

                // Process the data
                boolean updated = facade.updateUser(id, firstName, lastName, address);
                String responseMessage = updated ? "User updated" : "User not found";

                // Send the response back to the sender using the replyTo and correlationId
                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, responseMessage.getBytes(StandardCharsets.UTF_8));

                System.out.println(" [x] Response sent: '" + responseMessage + "'");
            };

            channel.basicConsume(UPDATE_USER_QUEUE, true, deliverCallback, consumerTag -> {
            });

            System.out.println(" [*] Waiting for messages...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUserStart() {
        try {
            Connection connection = RabbitMQUtils.getConnection();

            Channel channel = connection.createChannel();

            channel.queueDeclare(DELETE_USER_QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");

                JsonNode jsonNode = objectMapper.readTree(message);
                int id = Integer.valueOf(message);

                boolean deleted = facade.deleteUser(id);
                String responseMessage = deleted ? "User deleted" : "User not found";

                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, responseMessage.getBytes(StandardCharsets.UTF_8));

                System.out.println(" [x] Response sent: '" + responseMessage + "'");
            };

            channel.basicConsume(DELETE_USER_QUEUE, true, deliverCallback, consumerTag -> {
            });
            System.out.println(" [*] Waiting for messages....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MsgConsumer consumer = new MsgConsumer();
        consumer.createUserStart();
        consumer.getUserStart();
        consumer.getAllUsersStart();
        consumer.updateUserStart();
        consumer.deleteUserStart();
    }
}
