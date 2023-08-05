FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/application.yml application.yml
ENTRYPOINT ["java","-jar","/app.jar"]
