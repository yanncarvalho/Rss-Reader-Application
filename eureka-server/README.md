# ![Rss](https://img.shields.io/badge/rss-F88900?style=for-the-badge&logo=rss&logoColor=white) __Rss Reader Application - Eureka Server__ #

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

Rssreader Eureka Server is a service discovery to the microservice.

## How to run ##

### Using docker image ###

It can be run the project's docker image by the instruction:

``` sh
docker run -p [port]:[EUREKA_PORT] \
          --env EUREKA_PORT=[EUREKA_PORT] \
yanncarvalho/rssreader-eureka-server:latest
```

__i.e.__: EUREKA_PORT is optional.
By default _EUREKA_PORT_=80.

### Run without docker container ###

``` sh
mvn clean spring-boot:run
```

## Author ##

Made by [Yann Carvalho](https://www.linkedin.com/in/yann-carvalho-764abab6/).

## Licensing ##

Rss Reader Application is licensed under the Apache 2.0 License. See [LICENSE](../LICENSE) for the full license text.
