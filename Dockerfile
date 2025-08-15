# --- STAGE 1: Build the application ---
# Use a Maven image that includes JDK 17 to build the project
FROM maven:3.9-eclipse-temurin-17-focal AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml file first to leverage Docker layer caching
COPY pom.xml .

# Copy the rest of the source code
COPY src ./src

# Run the Maven package command to compile the code and create the JAR file.
# -DskipTests skips running tests to speed up the build.
RUN mvn package -DskipTests

# --- STAGE 2: Create the final, lightweight runtime image ---
# Use a slim JRE image which is much smaller than a full JDK
FROM openjdk:17-slim

# Set the working directory
WORKDIR /app

# Copy ONLY the compiled JAR file from the 'build' stage
COPY --from=build /app/target/greeter-api-*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# The command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]