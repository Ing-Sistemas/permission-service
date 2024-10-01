FROM openjdk:21-jdk
ARG JAR_FILE=build/libs/Permission-Service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /home/app.jar
RUN apt-get update && apt-get install -y curl
ENTRYPOINT ["java","-jar","/home/app.jar"]