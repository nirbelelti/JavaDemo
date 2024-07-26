package comment.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtils {

    public static Connection getConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(getEnv("RABBITMQ_HOST","localhost")); // Replace with your RabbitMQ server host
        factory.setPort(Integer.parseInt(getEnv("RABBITMQ_PORT", "5672"))); // Replace with your RabbitMQ server port
        factory.setUsername(getEnv("RABBITMQ_USER", "guest")); // Replace with your RabbitMQ server username
        factory.setPassword(getEnv("RABBITMQ_PASSWORD", "guest")); // Replace with your RabbitMQ server password
        return factory.newConnection();
    }
    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return value != null ? value : defaultValue;
    }
}
