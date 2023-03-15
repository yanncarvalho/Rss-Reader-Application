# Base
FROM golang:1.20-alpine AS base-stage
WORKDIR /app
COPY . /app
RUN go build -o app

# Production
FROM alpine:latest as production-stage
WORKDIR /app
COPY --from=base-stage /app/app .
COPY --from=base-stage /app/.env .
ENV MONGO_CRAWLER_PORT=8080
EXPOSE $MONGO_CRAWLER_PORT
CMD ["./app"]
