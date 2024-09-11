FROM openjdk:22-slim-bullseye

RUN apt-get update \
    && DEBIAN_FRONTEND=noninteractive \
    apt-get install -y maven

COPY . .

ENTRYPOINT sh -c "./mvnw package && java -jar target/crooodle-0.0.1-SNAPSHOT.jar"
