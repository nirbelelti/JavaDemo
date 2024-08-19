FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml /app

COPY src /app/src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

# Copy the packaged application from the build stage
COPY --from=build /app/target/CommentService-1.0-SNAPSHOT.jar /app/app.jar

# Command to run the application
CMD ["java", "-jar", "/app/app.jar"]
