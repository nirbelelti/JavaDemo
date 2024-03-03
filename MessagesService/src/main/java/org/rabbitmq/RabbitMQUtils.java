package org.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtils {

    public static Connection getConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Replace with your RabbitMQ server host
        factory.setPort(5672); // Replace with your RabbitMQ server port
        factory.setUsername("guest"); // Replace with your RabbitMQ server username
        factory.setPassword("guest"); // Replace with your RabbitMQ server password
        return factory.newConnection();
    }
}