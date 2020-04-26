FROM bellsoft/liberica-openjdk-alpine:13.0.1
ARG JAR_FILE=../../hw1_docker/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
