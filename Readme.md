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
```
├── DemoProject                # Root directory
│   └── CommentService         # Comment service
│       └── src                 # Source files
│           └── main            # Main application code
│           └── test            # Test step definitions    
│       └── target              # Compiled files
│       └── Dockerfile          # Dockerfile for Comment service
│       └── pom.xml             # Maven configuration file
│       └── DB.file            # (optional) SQLite database file will be created here after running the service
│   └── PostService            # Post service
│       └── src                 # Source files
│           └── main            # Main application code
│           └── test            # Test step definitions    
│       └── target              # Compiled files
│       └── Dockerfile          # Dockerfile for Comment service
│       └── pom.xml             # Maven configuration file
│       └── DB.file            # (optional) SQLite database file will be created here after running the service
│   └── UserService            # User service  
│       └── src                 # Source files
│           └── main            # Main application code
│           └── test            # Test step definitions    
│       └── target              # Compiled files
│       └── Dockerfile          # Dockerfile for Comment service
│       └── pom.xml             # Maven configuration file
│       └── DB.file            # (optional) SQLite database file will be created here after running the service  
│   └── RestService            # Rest service (Quarkus)       
├── README.md                # Project README
├── docker-compose.yml       # Docker-Compose file to set up RabbitMQ and other services up/down

```

### Services Overview

- **UserService**: Manages user information, including user creation, retrieval, update, and deletion.
- **PostService**: Manages post information, including post creation, retrieval, update, and deletion.
- **CommentService**: Manages comment information, including comment creation, retrieval, update, and deletion.
- **RestService**: A Quarkus service that acts as a REST API for the other services.

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

2. **Build the Project (for each service)**:
    ```sh
    mvn clean install
    ```
3. **Run the Tests**:
    ```sh
    mvn test
    ```
4. **Start maven Quarkus application**:
    ```sh
    mvn quarkus:dev
    ```

**FYI:**
1. The application will be running on port 8080
2. RabbitMQ will be running on port 5672 and can be accessed at http://localhost:15672 with username and password as guest
3. Make sure RabbitMQ is configured correctly and running on the default port (5672). If RabbitMQ is running on a different host or port, update the configuration in your application properties accordingly.






### Running the Application with Docker

1. **Clone the Repository**:
    ```sh
    git clone <repository-url>
    cd <repository-directory>
    ```

1. **Start All Services Using Docker-Compose**:
    ```sh
    docker-compose up -d
    ```

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