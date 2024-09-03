FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/notification-service-1.0-SNAPSHOT.jar /app/services.jar

EXPOSE 8084

LABEL authors="VirtualBag"

ENTRYPOINT ["java", "-jar", "/app/services.jar"]
