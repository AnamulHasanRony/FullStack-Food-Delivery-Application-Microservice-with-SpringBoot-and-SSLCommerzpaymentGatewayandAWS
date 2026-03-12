FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /build

COPY pom.xml .
COPY .mvn/ .mvn/
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/*-SNAPSHOT.jar app.jar

EXPOSE 8083

ENTRYPOINT ["sh","-c","java -XX:+UseContainerSupport -XX:MaxRAMPercentage=60.0 -XX:InitialRAMPercentage=20.0 -Dserver.port=${PORT:-8083} -jar app.jar"]




