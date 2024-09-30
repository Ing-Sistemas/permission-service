FROM gradle:8.7.0-jdk21 AS build

COPY . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle build

EXPOSE 8080

ENTRYPOINT ["java","-jar","/home/gradle/src/build/libs/Permission-Service-0.0.1-SNAPSHOT.jar"]