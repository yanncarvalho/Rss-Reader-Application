# ![Rss](https://img.shields.io/badge/rss-F88900?style=for-the-badge&logo=rss&logoColor=white) __Rss Reader Application - Cloud Gateway__ #

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

Rssreader Cloud Gateway is an API Gateway to the microservice.

## How to run ##

### Using docker image ###

It can be run the project's docker image by the instruction:

``` sh
docker run -p [port]:[GATEWAY_PORT] \
           --env GATEWAY_PORT=[GATEWAY_PORT] \
           --env EUREKA_SERVER=[EUREKA_SERVER] \
  yanncarvalho/rssreader-rssreader-cloud-gateway:latest
```

__i.e.__: EUREKA_SERVER must be informed the entire url; GATEWAY_PORT is optional.
By default _GATEWAY_PORT_=80.

[Read more about Eureka Server](../eureka-server/README.md "Read more about eureka-server").

### Run without docker container ###

To run the application without docker, it is necessary to inform the Eureka server URI:

``` sh
export EUREKA_SERVER=[EUREKA_SERVER] &&
mvn clean spring-boot:run
```

## Author ##

Made by [Yann Carvalho](https://www.linkedin.com/in/yann-carvalho-764abab6/).

## Licensing ##

Rss Reader Application is licensed under the Apache 2.0 License. See [LICENSE](../LICENSE) for the full license text.
