FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/discovery-server-1.0-SNAPSHOT.jar /app/services.jar

EXPOSE 8761

LABEL authors="VirtualBag"

ENTRYPOINT ["java", "-jar", "/app/services.jar"]
