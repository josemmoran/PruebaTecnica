FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./target/prueba-1.0.0.jar prueba-1.0.0.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","prueba-1.0.0.jar"] 
