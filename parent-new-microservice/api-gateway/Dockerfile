FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/api-gateway-1.0-SNAPSHOT.jar /app/services.jar

EXPOSE 8080

LABEL authors="VirtualBag"

ENTRYPOINT ["java", "-jar", "/app/services.jar"]
