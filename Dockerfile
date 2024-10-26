FROM openjdk:21-jdk
ARG JAR_FILE=build/libs/Permission-Service.jar
COPY ${JAR_FILE} /home/app.jar
ENTRYPOINT ["java","-jar","/home/app.jar"]