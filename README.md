## ![Rss](https://img.shields.io/badge/rss-F88900?style=for-the-badge&logo=rss&logoColor=white) Rss Reader Application ##

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Go](https://img.shields.io/badge/go-%2300ADD8.svg?style=for-the-badge&logo=go&logoColor=white)

 Rss Reader Application is a REST API that manages users accounts and provides xml+rss in json format.

Migration to Microservice with Spring Boot a legacy project in [jakarta EE](https://github.com/yanncarvalho/rss-reader-application-legacy)

[![Tests](https://github.com/yanncarvalho/rss-reader-application/actions/workflows/tests.yml/badge.svg)](https://github.com/yanncarvalho/rss-reader-application/actions/workflows/tests.yml)
[![Docker push](https://github.com/yanncarvalho/rss-reader-application/actions//workflows/docker.yml/badge.svg?branch=main)](https://github.com/yanncarvalho/rss-reader-application/actions//workflows/docker.yml)

## Built with ##

- Java 17
- Go  1.20
- MongoDb 4.4.6
- MySQL 8.0

## How to run ##

### Using docker compose ###

To run the application with docker compose run the command:

``` sh
export AUTH_DB_USERNAME=[mySQL_User] AUTH_DB_PASSWORD=[mySQL_Password] JWT_SECRET=[SECRET]&&
docker compose --env-file .env up
```

### Using docker image or without docker ###

See the README.md for each microservice:

- [See Eureka Server README.md](./eureka-server/ "See Eureka Server README.md").
- [See Config Server README.md](./config-server/ "See Config Server README.md").
- [See Auth README.md](./auth/ "See Auth README.md")
- [See Cloud Gateway README.md](./cloud-gateway/ "See Cloud Gateway README.md").
- [See Mongodb Crawler README.md](./mongodb-crawler/ "See Mongodb Crawler README.md").
- [See RSS README.md](./rss/ "See RSS README.md").

__The servers startup order must be:__

1. Eureka Server, and Config Server;
2. Auth, Cloud Gateway, and Mongodb Crawler;
3. RSS.

## Author ##

Made by [Yann Carvalho](https://www.linkedin.com/in/yann-carvalho-764abab6/).

## Licensing ##

Rss Reader Application is licensed under the Apache 2.0 License. See [LICENSE](LICENSE) for the full license text.
