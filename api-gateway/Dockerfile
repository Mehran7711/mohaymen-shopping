FROM docker.arvancloud.ir/maven:3.9.11-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

COPY api-gateway/pom.xml api-gateway/
COPY store-events/pom.xml store-events/
COPY store-service/pom.xml store-service/
COPY payment-service/pom.xml payment-service/

RUN --mount=type=cache,target=/root/.m2 \
    mvn -pl api-gateway -am dependency:go-offline -DskipTests

COPY api-gateway api-gateway
COPY store-events store-events
COPY store-service store-service
COPY payment-service payment-service

RUN --mount=type=cache,target=/root/.m2 \
    mvn -pl api-gateway -am package -DskipTests


FROM docker.arvancloud.ir/eclipse-temurin:21-jre

COPY --from=builder /app/api-gateway/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]