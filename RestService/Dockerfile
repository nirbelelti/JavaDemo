#Use Maven with JDK 11 as the base image
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven configuration file
COPY pom.xml /app

# Copy the source code
COPY src /app/src

# Package the application
RUN mvn clean package -DskipTests

# Use Eclipse Temurin JDK 17 for the runtime
FROM eclipse-temurin:17-jre

COPY ./target/quarkus-app /app

# Expose the port your application runs on
EXPOSE 8080

# Environment variables for JVM options
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0"

# Specify the command to run your application
CMD ["java", "-jar", "/app/quarkus-run.jar"]
