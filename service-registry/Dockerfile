FROM openjdk:17
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
#COPY target/service-registry-0.0.1-SNAPSHOT.jar app.jar
#
ENTRYPOINT ["java","-jar","/app.jar"]