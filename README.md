🛒 # **Shopping Cart Microservice Project** 
A scalable microservice project for CRUD product and inventory management and order service

🤖 ## **Techs**
- Java 21
- Spring boot 3.*
- Spring Security
- Lombok
- Spring Data JPA
- MySQL Driver
- Maven 3.*
- Spring Cloud Eureka server and discovery client
- Spring Cloud API-Gateway
- Spring Cloud brave tracer, Zipkin UI
- Spring Cloud Resilience4j circuit breaker
- Zookeeper and Kafka 3.*
- Prometheus & Grafana UI
- Docker


## **Getting Started**

!info ### Prerequisite 
- [Java 21](https://www.oracle.com/my/java/technologies/downloads/)
- [Maven 3.x](https://maven.apache.org/download.cgi)
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
- [Intellij IDE](https://www.jetbrains.com/idea/download/?section=windows)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

### Start & Running the application 

1. **Clone the repository**: `git clone https://github.com/ahmadhakimi/shopping-cart-microservice.git`
2. **Navigate to the project directory**: `cd shopping-cart-microservice`
3. **Build the application**: `mvn clean package`
4. **Build the docker container**: `docker compose up -d`
5. **Database Configuration**: please change your database config connectivity in the each of `application.properties` services.
6. **Run the application**:
   - Run the discovery-server first until the service is up and running
   - Run all the services including API-Gateway
   - Since i have not yet containerized the services, you should run the services separately

