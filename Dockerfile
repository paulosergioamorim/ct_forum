# Step 1: Use an official OpenJDK base image from Docker Hub
FROM openjdk:21-ea-1-jdk-slim

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the Spring Boot JAR file into the container
# Update the jar file when updates version of the application
COPY target/ct_forum-0.0.1-SNAPSHOT.jar /app/ct_forum-0.0.1-SNAPSHOT.jar

# Step 4: Expose the port your application runs on
EXPOSE 8080

# Step 5: Define the command to run your Spring Boot application
CMD ["java", "-jar", "/app/ct_forum-0.0.1-SNAPSHOT.jar"]