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
COPY --from=build /app/target/plugin-bridge-0.0.1-SNAPSHOT.jar plugin-bridge.jar

# Expose port 9001 to the outside world
EXPOSE 9001

# Run the JAR file
ENTRYPOINT ["java", "-jar", "plugin-bridge.jar"]