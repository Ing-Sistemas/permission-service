FROM openjdk:21-jdk
COPY build/libs/Permission-Service.jar /home/app.jar
ENTRYPOINT ["java","-jar","/home/app.jar"]