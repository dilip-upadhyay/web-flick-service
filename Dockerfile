# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Update the package list and install Maven
RUN apt-get update && apt-get install -y maven


# Copy the project files to the container
COPY . .

# Package the application
RUN mvn clean package -DskipTests

# Remove extra files
RUN rm -r /app/src
#
RUN rm -f /app/*

# Copy the built artifacts to the target directory
#RUN mkdir -p /app/target && mv -r target/* /app/target/
RUN jar xf target/web-flick-service-0.0.1-SNAPSHOT.jar
# Expose the port the app runs on
EXPOSE 8080

# Run the application
CMD java -classpath /app/target/classes:/app/BOOT-INF/lib/*  com.webflick.WebFlickApplication
#CMD mvn spring-boot:run