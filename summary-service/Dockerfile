FROM openjdk:17-jdk-alpine
EXPOSE 9595
ARG JAR_FILE=target/*.jar
COPY ./target/secondary-service.jar secondary-service.jar
ENTRYPOINT ["java","-jar","/secondary-service.jar"]