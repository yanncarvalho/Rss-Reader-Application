# Build stage
FROM golang:1.20-alpine AS build
WORKDIR /app
COPY . /app
RUN go build -o app

# Final stage
FROM alpine
WORKDIR /app
COPY --from=build /app/app .
COPY --from=build /app/.env .
ENV PORT=8080
EXPOSE $PORT
CMD ["./app"]
