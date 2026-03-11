FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/product-api-0.3.0-SNAPSHOT.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]