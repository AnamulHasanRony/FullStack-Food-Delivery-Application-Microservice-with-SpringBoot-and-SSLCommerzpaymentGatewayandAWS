FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/*-SNAPSHOT.jar app.jar

EXPOSE 8087

ENTRYPOINT ["sh","-c","java -XX:+UseContainerSupport -XX:MaxRAMPercentage=60.0 -XX:InitialRAMPercentage=20.0 -Dserver.port=${PORT:-8087} -jar app.jar"]




