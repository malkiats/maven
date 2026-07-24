# Multi-stage Dockerfile for Maven Java Application

# Stage 1: Build Stage
FROM maven:3.8.1-openjdk-11 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn clean dependency:resolve

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests

# Stage 2: Runtime Stage
FROM openjdk:11-jre-slim

WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /app/target/maven-java-app-1.0.0.jar app.jar

# Create a non-root user for security
RUN useradd -m -u 1000 appuser && chown appuser:appuser /app
USER appuser

# Expose port for Spring Boot application
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD java -cp app.jar com.example.HealthCheck || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["--server.port=8080"]
