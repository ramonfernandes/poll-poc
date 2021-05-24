# Work in progress

#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#EXPOSE 8085
#ARG JAR_FILE=./build/libs/poll-app-0.0.1-SNAPSHOT.jar
#
## Add the application's jar to the container
#ADD ${JAR_FILE} poll-app-0.0.1-SNAPSHOT.jar
#
## Run the jar file
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/poll-app-0.0.1-SNAPSHOT.jar"]