FROM openjdk:8-jdk-alpine

MAINTAINER liwenbo

ADD  target/mind-Java-1.0.1.jar  app.jar


EXPOSE 9090

ENTRYPOINT exec java -jar app.jar