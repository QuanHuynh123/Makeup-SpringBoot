FROM eclipse-temurin:17-jdk-focal

ARG JAR_FILE=target/makeup-images.jar

COPY ${JAR_FILE} app.jar
COPY secrets.env secrets.env
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]