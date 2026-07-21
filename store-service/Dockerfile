FROM docker.arvancloud.ir/maven:3.9.11-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

COPY store-events/pom.xml store-events/
COPY store-service/pom.xml store-service/
COPY payment-service/pom.xml payment-service/
COPY notification-service/pom.xml notification-service/
COPY api-gateway/pom.xml api-gateway/

RUN --mount=type=cache,target=/root/.m2 \
    mvn -pl store-service -am dependency:go-offline -DskipTests

COPY store-events store-events
COPY store-service store-service
COPY payment-service payment-service
COPY notification-service notification-service
COPY api-gateway api-gateway

RUN --mount=type=cache,target=/root/.m2 \
    mvn -pl store-service -am package -DskipTests


FROM docker.arvancloud.ir/eclipse-temurin:21-jre

COPY --from=builder /app/store-service/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]