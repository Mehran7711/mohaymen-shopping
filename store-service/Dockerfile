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
