# https://registry.hub.docker.com/r/theasp/clojurescript-nodejs/tags?name=alpine
FROM theasp/clojurescript-nodejs:alpine AS BUILD
MAINTAINER Pragmint Bot <pragbot@pragmint.com>
COPY . /code
WORKDIR /code
RUN lein compile-min

# https://hub.docker.com/_/openjdk/tags?name=11.0.2-jre
FROM openjdk:11.0.2-jre-slim
MAINTAINER Pragmint Bot <pragbot@pragmint.com>
WORKDIR /app
COPY --from=BUILD /code/target/*-standalone.jar ./app.jar
EXPOSE 8085
CMD ["java", "-jar", "app.jar", "8085"]
