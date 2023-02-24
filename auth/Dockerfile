# Base
FROM eclipse-temurin:17-jdk-alpine as base-stage
COPY . .
RUN ./mvnw clean install

# Production
FROM eclipse-temurin:17-jre-alpine as production-stage
COPY --from=base-stage target/*.jar app.jar
ARG PORT=80
ARG PROFILE=prod
ARG db.schema
ARG db.host
ARG db.port
ARG db.password
ARG db.username
ARG jwt.secret
ARG jwt.duration-in-hours
ENV PROFILE=$PROFILE
ENV PORT=$PORT
EXPOSE $PORT
ENTRYPOINT ["java","-Dspring.profiles.active=${PROFILE}", "-jar", "app.jar"]



