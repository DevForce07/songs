FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

COPY . .

RUN ./gradlew clean build

FROM openjdk:17-jdk-slim
EXPOSE 8888
EXPOSE 80
EXPOSE 443

COPY /build/libs/songs-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar","app.jar"]