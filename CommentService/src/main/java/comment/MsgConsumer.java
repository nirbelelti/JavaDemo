package comment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import comment.config.RabbitMQUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MsgConsumer {
    private final static String CREATE_COMMENT_QUEUE = "createCommentQueue";
    private final static String UPDATE_COMMENT_QUEUE = "updateCommentQueue";
    private final static String DELETE_COMMENT_QUEUE = "deleteCommentQueue";
    private final static String GET_COMMENT_BY_POST_QUEUE = "getCommentsByPostQueue";

    private final ObjectMapper objectMapper;
    private final CommentFacade facade;
    private final Channel channel;


    ArrayList<String> result = new ArrayList<>();

    public MsgConsumer(ObjectMapper objectMapper, CommentFacade facade, Channel channel) {
        this.objectMapper = objectMapper;
        this.facade = facade;
        this.channel = channel;
    }

    public void start() {
        createCommentStart();
        updateCommentStart();
        deleteCommentStart();
        getCommentsByPostIdStart();
    }


    public void createCommentStart() {
        try {
            // Establish a connection to RabbitMQ server
            Connection connection = RabbitMQUtils.getConnection();

            // Create a channel
            Channel channel = connection.createChannel();

            // Declare a queue (creates the queue if not exists)
            channel.queueDeclare(CREATE_COMMENT_QUEUE, false, false, false, null);

            // Create a consumer and specify the callback to handle incoming messages
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received create'" + message + "'");

                // Extract data from the message
                JsonNode jsonNode = objectMapper.readTree(message);
                System.out.println(" Json create'" + jsonNode + "'");
                int postId = jsonNode.get("postId").asInt();
                int userId = jsonNode.get("userId").asInt();
                String body = jsonNode.get("body").asText();
                System.out.println("userID: " + userId + " postId: " + postId + " body: " + body);

                // Process the data
                int id = facade.createComment(postId, userId, body);
                System.out.println(" [x] Comment created with id: '" + id + "'");
                Comment Comment = new Comment(id, body, postId, userId);
                String responseMessage = Comment.toString();

                // Send the response back to the sender using the replyTo and correlationId
                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, responseMessage.getBytes(StandardCharsets.UTF_8));

                System.out.println(" [x] Response sent: '" + responseMessage + "'");
            };

            // Consume messages from the queue
            channel.basicConsume(CREATE_COMMENT_QUEUE, true, deliverCallback, consumerTag -> {
            });

            // Keep the consumer running (you can add your own logic to gracefully stop the consumer)
            System.out.println(" [*] Waiting for messages. To exit press Ctrl+C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCommentStart() {
        try {
            Connection connection = RabbitMQUtils.getConnection();

            Channel channel = connection.createChannel();

            channel.queueDeclare(UPDATE_COMMENT_QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
                System.out.println(" updateCommentStart");
                JsonNode jsonNode = objectMapper.readTree(message);
                int id = jsonNode.get("id").asInt();
                int userId = jsonNode.get("userId").asInt();
                int postId = jsonNode.get("postId").asInt();
                String body = jsonNode.get("body").asText();

                Comment comment = new Comment(id, body, postId, userId);
                System.out.println(" Comment: " + comment.toString());
                // Process the data
                comment = facade.updateComment(comment);
                System.out.println(" [x] Comment updated: '" + comment.toString() + "'");
                String responseMessage = comment.toString();

                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, responseMessage.getBytes(StandardCharsets.UTF_8));

                System.out.println(" [x] Response sent: '" + responseMessage + "'");
            };

            channel.basicConsume(UPDATE_COMMENT_QUEUE, true, deliverCallback, consumerTag -> {
            });

            System.out.println(" [*] Waiting for messages...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCommentStart() {
        try {
            Connection connection = RabbitMQUtils.getConnection();

            Channel channel = connection.createChannel();

            channel.queueDeclare(DELETE_COMMENT_QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");

                JsonNode jsonNode = objectMapper.readTree(message);
                int id = jsonNode.get("id").asInt();

                String responseMessage = "Comment " + id + " not found";
                try {
                    facade.deleteComment(id);
                    responseMessage = "Comment deleted";
                } catch (Exception e) {
                    e.printStackTrace();
                }


                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, responseMessage.getBytes(StandardCharsets.UTF_8));

                System.out.println(" [x] Response sent: '" + responseMessage + "'");
            };

            channel.basicConsume(DELETE_COMMENT_QUEUE, true, deliverCallback, consumerTag -> {
            });
            System.out.println(" [*] Waiting for messages....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> convertToString(ArrayList<Comment> posts) {
        ArrayList<String> result = new ArrayList<>();
        for (Comment post : posts) {
            result.add(post.toString());
        }
        return result;
    }

    public synchronized void getCommentsByPostIdStart() {

        try {
            Connection connection = RabbitMQUtils.getConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(GET_COMMENT_BY_POST_QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

                JsonNode jsonNode = objectMapper.readTree(message);
                int postId = jsonNode.get("postId").asInt();

                result = convertToString(facade.getCommentByPost(postId));

                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, result.toString().getBytes(StandardCharsets.UTF_8));
            };
            channel.basicConsume(GET_COMMENT_BY_POST_QUEUE, true, deliverCallback, consumerTag -> {
            });

            System.out.println(" [*] Waiting for messages new. To exit press Ctrl+C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // Initialize necessary components
            ObjectMapper objectMapper = new ObjectMapper();
            CommentFacade facade = new CommentFacade();

            Connection connection = RabbitMQUtils.getConnection();
            Channel channel = connection.createChannel();

            MsgConsumer consumer = new MsgConsumer(objectMapper, facade, channel);

            consumer.start();

            System.out.println(" [*] Waiting for messages. To exit press Ctrl+C");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
