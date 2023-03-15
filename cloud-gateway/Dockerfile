# Base
FROM eclipse-temurin:17-jdk-alpine as base-stage
COPY . .
RUN ./mvnw clean install

# Production
FROM eclipse-temurin:17-jre-alpine as production-stage
COPY --from=base-stage target/*.jar app.jar
ARG GATEWAY_PORT=80
ARG EUREKA_SERVER
ENV GATEWAY_PORT=$GATEWAY_PORT
EXPOSE $GATEWAY_PORT
ENTRYPOINT ["java", "-jar", "app.jar"]



