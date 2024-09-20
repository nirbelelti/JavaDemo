# Demo Project: Microservices Simulation with TDD, Maven, Java, RabbitMQ, Cucumber, Docker, and Docker-Compose

## Overview

This project is a demonstration of a microservices architecture using various technologies such as Java, Maven, RabbitMQ, Cucumber, Docker, and Docker-Compose. The project showcases how to implement Test-Driven Development (TDD) in a microservices environment, with a focus on communication between services using RabbitMQ.

## Technologies Utilized

- **Java**: The primary programming language used for developing the microservices.
- **Maven**: A build automation tool used for managing project dependencies and building the project.
- **Spring Boot**: A framework used to create stand-alone, production-grade Spring-based applications.
- **RabbitMQ**: A message broker used for communication between microservices.
- **Cucumber**: A testing framework that supports Behavior-Driven Development (BDD).
- **Docker**: A platform used to develop, ship, and run applications inside containers.
- **Docker-Compose**: A tool for defining and running multi-container Docker applications.
- **SQLite**: A lightweight, file-based database used for data storage.

## Project Structure

- `src/main/java`: Contains the main application code.
- `src/test/java`: Contains the test code, including Cucumber step definitions.
- `src/main/resources`: Contains configuration files.
- `docker-compose.yml`: Docker-Compose file to set up the RabbitMQ service and other dependencies.

## How to Use

### Prerequisites

- Java 11 or higher
- Maven
- Docker
- Docker-Compose

### Running the Application

1. **Clone the Repository**:
    ```sh
    git clone <repository-url>
    cd <repository-directory>
    ```

2. **Build the Project**:
    ```sh
    mvn clean install
    ```

3. **Start RabbitMQ using Docker-Compose**:
    ```sh
    docker-compose up -d
    ```

4. **Run the Application**:
    ```sh
    mvn spring-boot:run
    ```

5. **Run the Tests**:
    ```sh
    mvn test
    ```

### Running the Application with Docker

1. **Start All Services Using Docker-Compose**:
    ```sh
    docker-compose up -d
    ```

2. **Run Each Service Independently**:
   Each service can also be run independently using its own Dockerfile. Navigate to the service directory and build and run the Docker container:
    ```sh
    cd <service-directory>
    docker build -t <service-name> .
    docker run -p <port>:<port> <service-name>
    ```

### Running the Application Locally Without Docker

To run the application locally without using Docker, follow these steps:

### Prerequisites

- Java 11 or higher
- Maven
- RabbitMQ installed and running locally

### Steps

1. **Clone the Repository**:
    ```sh
    git clone <repository-url>
    cd <repository-directory>
    ```

2. **Build the Project**:
    ```sh
    mvn clean install
    ```

3. **Start RabbitMQ**:
   Ensure RabbitMQ is installed and running on your local machine. You can download and install RabbitMQ from [here](https://www.rabbitmq.com/download.html).

4. **Run the Application**:
    ```sh
    mvn spring-boot:run
    ```

5. **Run the Tests**:
    ```sh
    mvn test
    ```

Make sure RabbitMQ is configured correctly and running on the default port (5672). If RabbitMQ is running on a different host or port, update the configuration in your application properties accordingly.

## Features

- **User Management**: Create, read, update, and delete user information.
- **Message Queue**: Use RabbitMQ for communication between services.
- **TDD and BDD**: Implement tests using JUnit and Cucumber.

## Limitations

Due to the scope of the demo, the following aspects were not fully implemented:

- **Security**: Authentication and authorization mechanisms were not included.
- **Database Integration**: The project uses SQLite, which is a lightweight, file-based database. It is not suitable for production environments where a more robust and scalable database solution would be required.
- **Scalability**: The demo does not cover scaling the microservices or RabbitMQ setup.
- **Monitoring and Logging**: Advanced monitoring and logging setups were not included.

## Conclusion

This project serves as a basic demonstration of how to set up a microservices architecture using Java, Maven, RabbitMQ, Cucumber, Docker, and Docker-Compose. It provides a foundation for further development and integration of additional features and services.

## License

This project is licensed under the MIT License.