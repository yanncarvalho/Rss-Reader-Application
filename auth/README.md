# ![Rss](https://img.shields.io/badge/rss-F88900?style=for-the-badge&logo=rss&logoColor=white) __Rss Reader Application - Auth__ #

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

Rssreader auth provides JWT authentication to the microservice.

## How to run ##

### Using docker image ###

It can be run the project's docker image by the instruction:

``` sh
docker run -p [port]:[AUTH_PORT] \
           --env PROFILE_AUTH=[prod|dev] \
           --env AUTH_DB_PORT=[AUTH_DB_PORT] \
           --env AUTH_DB_SCHEMA=[AUTH_DB_SCHEMA] \
           --env db.host=[db.host] \
           --env db.password=[db.password] \
           --env db.username=[AUTH_DB_USERNAME] \
           --env jwt.secret=[jwt.secret] \
           --env jwt.duration-in-hours=[jwt.duration-in-hours] \
           --env AUTH_PORT=[AUTH_PORT] \
           --env EUREKA_SERVER=[EUREKA_SERVER] \ 
           --env CONFIG_SERVER=[CONFIG_SERVER] \
  yanncarvalho/rssreader-auth:latest
```

__i.e.__: CONFIG_SERVER and EUREKA_SERVER must be informed the entire url; CONFIG_SERVER, jwt.duration-in-hours and AUTH_PORT are optional.
By default _jwt.duration-in-hours_=2; _PROFILE_AUTH_=prod and _AUTH_PORT_=80

Read more about [Eureka Server](../eureka-server/README.md "Read more about eureka-server") and [Config Server](../config-server/README.md "Read more about config-server").

#### __If you will not use config server you must change some env variable:__ ####

- instead of db.username by spring.datasource.username
- instead of db.password by spring.datasource.password
- instead of db.host, db.port, and db.schema by spring.datasource.url=jdbc:mysql://[db.host:db.port/db.schema]

### Run without docker container ###

To run the application without docker, it is necessary to inform which profile will be executed using the PROFILE_AUTH environment variable (by default its value is development _dev_):

``` sh
export PROFILE_AUTH=[dev|prod]  spring.datasource.username=[db.username] spring.datasource.password=[db.password] spring.datasource.url=jdbc:mysql://[db.host:db.port/db.schema] &&
mvn clean spring-boot:run
```

## Author ##

Made by [Yann Carvalho](https://www.linkedin.com/in/yann-carvalho-764abab6/).

## Licensing ##

Rss Reader Application is licensed under the Apache 2.0 License. See [LICENSE](../LICENSE) for the full license text.
