FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/Makeup-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar
COPY secrets.env secrets.env
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
