ARG MAVEN_IMAGE=docker.arvancloud.ir/maven:3.9.11-eclipse-temurin-21
FROM ${MAVEN_IMAGE} AS builder

WORKDIR /app

COPY pom.xml .

COPY api-gateway/pom.xml api-gateway/
COPY store-events/pom.xml store-events/
COPY store-service/pom.xml store-service/
COPY payment-service/pom.xml payment-service/
COPY notification-service/pom.xml notification-service/
COPY store-webapp/pom.xml store-webapp/

RUN --mount=type=cache,target=/root/.m2 \
    n=0; \
    until mvn -pl api-gateway -am dependency:go-offline -DskipTests \
        -Daether.connector.connectTimeout=60000 \
        -Daether.connector.requestTimeout=300000; do \
      n=$((n+1)); \
      if [ "$n" -ge 3 ]; then exit 1; fi; \
      echo "Maven Central timed out, retry ${n}/3"; \
      sleep 15; \
    done

COPY api-gateway api-gateway
COPY store-events store-events
COPY store-service store-service
COPY payment-service payment-service
COPY notification-service notification-service

RUN --mount=type=cache,target=/root/.m2 \
    n=0; \
    until mvn -pl api-gateway -am package -DskipTests \
        -Daether.connector.connectTimeout=60000 \
        -Daether.connector.requestTimeout=300000; do \
      n=$((n+1)); \
      if [ "$n" -ge 3 ]; then exit 1; fi; \
      echo "Maven Central timed out, retry ${n}/3"; \
      sleep 15; \
    done


FROM ${MAVEN_IMAGE}

COPY --from=builder /app/api-gateway/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]