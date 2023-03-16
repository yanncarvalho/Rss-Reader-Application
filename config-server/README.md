# ![Rss](https://img.shields.io/badge/rss-F88900?style=for-the-badge&logo=rss&logoColor=white) __Rss Reader Application - Config Server__ #

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

Rssreader Config Server is a configuration server to the microservice.

## How to run ##

### Using docker image ###

It can be run the project's docker image by the instruction:

``` sh
docker run -p [port]:[CONFIG_SERVER_PORT] \
        --env CONFIG_SERVER_PORT=[CONFIG_SERVER_PORT] \
        yanncarvalho/rssreader-config-server:latest
```

__i.e.__: CONFIG_SERVER_PORT is optional.
By default _CONFIG_SERVER_PORT_=80.

### Run without docker container ###

``` sh
mvn clean spring-boot:run
```

## Author ##

Made by [Yann Carvalho](https://www.linkedin.com/in/yann-carvalho-764abab6/).

## Licensing ##

Rss Reader Application is licensed under the Apache 2.0 License. See [LICENSE](../LICENSE) for the full license text.
