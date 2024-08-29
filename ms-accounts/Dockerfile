# # Use an official OpenJDK runtime as a parent image
# FROM openjdk:17-jdk-slim

# # Set the working directory inside the container
# WORKDIR /app

# # Copy the application JAR file into the container
# COPY target/ms-accounts-0.0.1-SNAPSHOT.jar ms-accounts.jar

# # Expose port 8761 to the outside world
# EXPOSE 9001

# # Run the JAR file
# ENTRYPOINT ["java", "-jar", "eureka-server.jar"]

# Use an official Maven image to build the project
FROM maven:3.8.5-openjdk-17-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file from the build stage
COPY --from=build /app/target/ms-accounts-0.0.1-SNAPSHOT.jar ms-accounts.jar

# Expose port 9001 to the outside world
EXPOSE 8081

# Run the JAR file
ENTRYPOINT ["java", "-jar", "ms-accounts.jar"]