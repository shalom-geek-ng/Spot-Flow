# Step 1: Use Maven to build the project
FROM maven:3.6.3-openjdk-17-slim AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

# Step 2: Use the official OpenJDK 17 image as a base image
FROM openjdk:17-ea-18-jdk-slim
WORKDIR /app
COPY --from=build /app/target/SpotFlow-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Step 3: Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
