FROM docker.arvancloud.ir/eclipse-temurin:21-jre

# Application directory
WORKDIR /app

# Copy the Spring Boot application
COPY target/store-service-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8081

# Optional JVM options
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]