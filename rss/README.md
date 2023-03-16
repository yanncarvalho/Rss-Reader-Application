# ![Rss](https://img.shields.io/badge/rss-F88900?style=for-the-badge&logo=rss&logoColor=white) __Rss Reader Application - RSS__ #

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)

RSS Reader RSS provides Rss xml to json converter by url.

## How to run ##

### Using docker image ###

It can be run the project's docker image by the instruction:

``` sh
docker run -p [port]:[RSS_PORT] \
           --env PROFILE_RSS=[prod|dev]
           --env RSS_PORT=[RSS_PORT] \
           --env EUREKA_SERVER=[EUREKA_SERVER] \
           --env CONFIG_SERVER=[CONFIG_SERVER] \
           --env MONGO_DATABASE=[MONGO_DATABASE] \
           --env MONGO_URI=[MONGO_URI] \
  yanncarvalho/rssreader-rss:latest
```

__i.e.__: CONFIG_SERVER and EUREKA_SERVER must be informed the entire url; CONFIG_SERVER, PROFILE_RSS and RSS_PORT are optional.
By default _RSS_PORT_=80 and _PROFILE_RSS_=prod. __It is necessary to have rssreader-auth running.__

#### __If you will not use config server you must change some env variable:__ ####

- instead of MONGO_DATABASE by spring.data.mongodb.database
- instead of MONGO_URI by spring.data.mongodb.uri

Read more about [Eureka Server](../eureka-server/README.md "Read more about Eureka Server"), [Config Server](../config-server/README.md "Read more about Config Server"), and [Auth](../auth/README.md "Read more about auth").

### Run without docker container ###

To run the application without docker, it is necessary to inform which profile will be executed using the PROFILE_RSS environment variable (by default its value is development _dev_):

``` sh
export PROFILE_RSS=[dev|prod] spring.data.mongodb.uri=[MONGO_URI] spring.data.mongodb.database=[MONGO_DATABASE]  &&
mvn clean spring-boot:run
```

## Author ##

Made by [Yann Carvalho](https://www.linkedin.com/in/yann-carvalho-764abab6/).

## Licensing ##

Rss Reader Application is licensed under the Apache 2.0 License. See [LICENSE](../LICENSE) for the full license text.
