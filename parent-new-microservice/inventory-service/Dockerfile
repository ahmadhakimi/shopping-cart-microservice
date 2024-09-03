FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/inventory-service-1.0-SNAPSHOT.jar /app/services.jar

EXPOSE 8083

LABEL authors="VirtualBag"

ENTRYPOINT ["java", "-jar", "/app/services.jar"]