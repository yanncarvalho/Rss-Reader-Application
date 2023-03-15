# Base
FROM eclipse-temurin:17-jdk-alpine as base-stage
COPY . .
RUN ./mvnw clean install

# Production
FROM eclipse-temurin:17-jre-alpine as production-stage
COPY --from=base-stage target/*.jar app.jar
ARG CONFIG_SERVER_PORT=80
ARG MONGO_URI
ARG MONGO_DATABASE
ARG db.schema
ARG db.host
ARG db.port
ARG db.password
ARG db.username
ARG jwt.secret
ARG jwt.duration-in-hours
ENV CONFIG_SERVER_PORT=$CONFIG_SERVER_PORT
EXPOSE $CONFIG_SERVER_PORT
ENTRYPOINT ["java", "-jar", "app.jar"]





