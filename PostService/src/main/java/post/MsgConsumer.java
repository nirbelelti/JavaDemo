package post;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.rabbitmq.RabbitMQUtils;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MsgConsumer {
    private final static String CREATE_POST_QUEUE = "createPostQueue";
    private final static String GET_POST_QUEUE = "getPostQueue";
    private final static String GET_ALL_POSTS_QUEUE = "getAllPostsQueue";
    private final static String UPDATE_POST_QUEUE = "updatePostQueue";
    private final static String DELETE_POST_QUEUE = "deletePostQueue";
    private final static String GET_POST_BY_USER_QUEUE = "getPostByUserQueue";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final PostFacade facade = new PostFacade();

    ArrayList<String> result = new ArrayList<>();
    ArrayList<Post> posts = new ArrayList<>();


    public void createPostStart() {
        try {
            // Establish a connection to RabbitMQ server
            Connection connection = RabbitMQUtils.getConnection();

            // Create a channel
            Channel channel = connection.createChannel();

            // Declare a queue (creates the queue if not exists)
            channel.queueDeclare(CREATE_POST_QUEUE, false, false, false, null);

            // Create a consumer and specify the callback to handle incoming messages
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received create'" + message + "'");

                // Extract data from the message
                JsonNode jsonNode = objectMapper.readTree(message);
                System.out.println(" Json create'" + jsonNode + "'");
                int userID = jsonNode.get("userId").asInt();
                String title = jsonNode.get("title").asText();
                String body = jsonNode.get("body").asText();
                System.out.println("userID: " + userID + " title: " + title + " body: " + body);

                // Process the data
                int postId = facade.createPost(userID, title, body);
                System.out.println(" [x] Post created with id: '" + postId + "'");
                Post Post = new Post(postId, userID, title, body);
                String responseMessage = Post.toString();

                // Send the response back to the sender using the replyTo and correlationId
                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, responseMessage.getBytes(StandardCharsets.UTF_8));

                System.out.println(" [x] Response sent: '" + responseMessage + "'");
            };

            // Consume messages from the queue
            channel.basicConsume(CREATE_POST_QUEUE, true, deliverCallback, consumerTag -> {
            });

            // Keep the consumer running (you can add your own logic to gracefully stop the consumer)
            System.out.println(" [*] Waiting for messages. To exit press Ctrl+C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllPostsStart() {
        try {
            Connection connection = RabbitMQUtils.getConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(GET_ALL_POSTS_QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {

                result = convertToString(facade.allPosts());

                String Posts = result.toString();
                System.out.println(" Result: " + result.toString());

                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, Posts.getBytes(StandardCharsets.UTF_8));
            };
            channel.basicConsume(GET_ALL_POSTS_QUEUE, true, deliverCallback, consumerTag -> {
            });

            System.out.println(" [*] Waiting for messages. To exit press Ctrl+C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPostStart() {
        try {
            Connection connection = RabbitMQUtils.getConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(GET_POST_QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

                JsonNode jsonNode = objectMapper.readTree(message);
                int id = jsonNode.get("id").asInt();

                String post = facade.getPost(id);

                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, post.getBytes(StandardCharsets.UTF_8));
            };

            channel.basicConsume(GET_POST_QUEUE, true, deliverCallback, consumerTag -> {
            });

            System.out.println(" [*] Waiting for messages...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePostStart() {
        try {
            Connection connection = RabbitMQUtils.getConnection();

            Channel channel = connection.createChannel();

            channel.queueDeclare(UPDATE_POST_QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
                JsonNode jsonNode = objectMapper.readTree(message);
                int id = jsonNode.get("id").asInt();
                int userId = jsonNode.get("userId").asInt();
                String title = jsonNode.get("title").asText();
                String body = jsonNode.get("body").asText();

                // Process the data
                boolean updated = facade.updatePost(id,userId, title, body);
                String responseMessage = updated ? "Post updated" : "Post not found";

                // Send the response back to the sender using the replyTo and correlationId
                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, responseMessage.getBytes(StandardCharsets.UTF_8));

                System.out.println(" [x] Response sent: '" + responseMessage + "'");
            };

            channel.basicConsume(UPDATE_POST_QUEUE, true, deliverCallback, consumerTag -> {
            });

            System.out.println(" [*] Waiting for messages...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePostStart() {
        try {
            Connection connection = RabbitMQUtils.getConnection();

            Channel channel = connection.createChannel();

            channel.queueDeclare(DELETE_POST_QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");

                JsonNode jsonNode = objectMapper.readTree(message);
                int id = Integer.valueOf(message);

                boolean deleted = facade.deletePost(id);
                String responseMessage = deleted ? "Post deleted" : "Post not found";

                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, responseMessage.getBytes(StandardCharsets.UTF_8));

                System.out.println(" [x] Response sent: '" + responseMessage + "'");
            };

            channel.basicConsume(DELETE_POST_QUEUE, true, deliverCallback, consumerTag -> {
            });
            System.out.println(" [*] Waiting for messages....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private ArrayList<String> convertToString(ArrayList<Post> posts) {
        ArrayList<String> result = new ArrayList<>();
        for (Post post : posts) {
            result.add(post.toString());
        }
        return result;
    }

    public void getPostByUserStart() {
        try {
            Connection connection = RabbitMQUtils.getConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(GET_POST_BY_USER_QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

                JsonNode jsonNode = objectMapper.readTree(message);
                int userId = jsonNode.get("userId").asInt();

                result = convertToString(facade.getAllPostsByUser(userId));

                String Posts = result.toString();
                System.out.println(" Result found: " + result.toString());

                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, Posts.getBytes(StandardCharsets.UTF_8));
            };
            channel.basicConsume(GET_POST_BY_USER_QUEUE, true, deliverCallback, consumerTag -> {
            });

            System.out.println(" [*] Waiting for messages. To exit press Ctrl+C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MsgConsumer consumer = new MsgConsumer();
        consumer.createPostStart();
        consumer.getPostStart();
        consumer.getAllPostsStart();
        consumer.updatePostStart();
        consumer.deletePostStart();
        consumer.getPostByUserStart();
    }
}
