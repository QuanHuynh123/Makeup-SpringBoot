
FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/your-app.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
