# Base
FROM eclipse-temurin:17-jdk-alpine as base-stage
COPY . .
RUN ./mvnw clean install

# Production
FROM eclipse-temurin:17-jre-alpine as production-stage
COPY --from=base-stage target/*.jar app.jar
ARG RSS_PORT=80
ARG PROFILE_RSS=prod
ARG EUREKA_SERVER
ENV PROFILE=$PROFILE_RSS
ENV RSS_PORT=$RSS_PORT
EXPOSE $RSS_PORT
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "app.jar"]



