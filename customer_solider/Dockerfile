FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/customer_solider-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 3040
ENTRYPOINT ["java", "-jar", "app.jar"]
