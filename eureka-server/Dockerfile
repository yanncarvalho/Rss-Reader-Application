# Base
FROM eclipse-temurin:17-jdk-alpine as base-stage
COPY . .
RUN ./mvnw clean install

# Production
FROM eclipse-temurin:17-jre-alpine as production-stage
COPY --from=base-stage target/*.jar app.jar
ARG EUREKA_PORT=80
ENV EUREKA_PORT=$EUREKA_PORT
EXPOSE $EUREKA_PORT
ENTRYPOINT ["java", "-jar", "app.jar"]



