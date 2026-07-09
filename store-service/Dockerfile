FROM docker.arvancloud.ir/maven:3.9.11-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY store-events store-events
COPY store-service store-service
COPY payment-service payment-service

RUN mvn -pl store-service -am package -DskipTests

FROM docker.arvancloud.ir/eclipse-temurin:21-jre

COPY --from=builder /app/store-service/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

## ==========================
## Stage 1 - Build
## ==========================
#FROM docker.arvancloud.ir/maven:3.9.9-eclipse-temurin-21 AS builder
#
#WORKDIR /app
#
## Copy pom.xml first (improves Docker cache)
#COPY pom.xml .
#
## Download dependencies
#RUN mvn dependency:go-offline
#
## Copy source code
#COPY src ./src
#
## Build the application
#RUN mvn clean package -DskipTests
#
## ==========================
## Stage 2 - Runtime
## ==========================
#FROM docker.arvancloud.ir/eclipse-temurin:21-jre
#
#WORKDIR /app
#
#COPY --from=builder /app/target/*.jar app.jar
#
#EXPOSE 8081
#
#ENV JAVA_OPTS=""
#
#ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]