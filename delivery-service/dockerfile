FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/delivery-service.jar /app/delivery-service.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "/app/delivery-service.jar"]
