# ![Rss](https://img.shields.io/badge/rss-F88900?style=for-the-badge&logo=rss&logoColor=white) __Rss Reader Application - Mongodb Crawler__ #

![Go](https://img.shields.io/badge/go-%2300ADD8.svg?style=for-the-badge&logo=go&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)

Rssreader Mongodb Crawler provides a parser that looks for expired RSSes and update them.

## How to run ##

### Using docker image ###

It can be run the project's docker image by the instruction:

``` sh
docker run -p [port]:[MONGO_CRAWLER_PORT] \
           --env MONGO_CRAWLER_PORT=[MONGO_CRAWLER_PORT] \
           --env CONFIG_SERVER_URI=[CONFIG_SERVER_URI] \
           --env CONFIG_SERVER_VAR_MONGO_URI=[CONFIG_SERVER_VAR_MONGO_URI] \
           --env CONFIG_SERVER_VAR_MONGO_DATABASE=[CONFIG_SERVER_VAR_MONGO_DATABASE] \
           --env MONGODB_URI=[MONGODB_URI] \
           --env MONGODB_DATABASE=[MONGODB_DATABASE] \
           --env MONGODB_DOCUMENT=[MONGODB_DOCUMENT] \
  yanncarvalho/rssreader-mongodb-crawler:latest
```

__i.e.__: CONFIG_SERVER must be informed the entire url; CONFIG_SERVER, jwt.CONFIG_SERVER_VAR_MONGO_URI and CONFIG_SERVER_VAR_MONGO_DATABASE are not necessary if MONGODB_URI and MONGODB_DATABASE are informed.

By default _MONGODB_DATABASE_=rssreader_rss; _MONGODB_URI_=rss and _MONGO_CRAWLER_PORT_=8080

Read more about [Config Server](../config-server/README.md "Read more about config-server").

### Run without docker container ###

To run the application without docker, it need be informed values ​​for parameters in the ./env file ( MONGODB_URI or  CONFIG_SERVER_URI, optinal):

``` sh
go run main.go
```

## Author ##

Made by [Yann Carvalho](https://www.linkedin.com/in/yann-carvalho-764abab6/).

## Licensing ##

Rss Reader Application is licensed under the Apache 2.0 License. See [LICENSE](../LICENSE) for the full license text.
