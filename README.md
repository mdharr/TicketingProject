# Ticketing System Microservices

A comprehensive event ticket booking system built with Spring Boot microservices architecture.

## Project Overview

This project is a microservices-based implementation of a ticket booking system that allows users to browse events, check inventory, make bookings, and process orders. The system follows modern cloud-native patterns with separate, independently deployable services communicating through REST APIs and asynchronous messaging.

## Architecture

The application consists of the following microservices:

### API Gateway
- Acts as the single entry point for all client requests
- Routes requests to appropriate microservices
- Handles authentication and authorization via Keycloak integration
- Implements security configurations

### Booking Service
- Manages customer information
- Handles event booking requests
- Communicates with inventory service to check availability
- Uses event-driven architecture to notify order service

### Inventory Service
- Manages venue and event information
- Tracks available tickets for events
- Provides inventory status
- Updates inventory based on bookings

### Order Service
- Processes order requests
- Maintains order history
- Communicates with booking service for order fulfillment
- Persists order data

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.4.3
- Spring Cloud 2024.0.0
- Spring Cloud Gateway MVC
- Spring Data JPA
- Spring Kafka for messaging
- RESTful API architecture
- OpenAPI/Springdoc (Swagger) for API documentation
- OAuth2 Resource Server for authentication
- Resilience4j for circuit breaking
- Flyway for database migrations
- MySQL for data persistence
- Maven for dependency management
- Docker for containerization
- Lombok for reducing boilerplate code

## System Flow

1. Client authenticates through Keycloak via the API Gateway
2. API Gateway routes requests to the appropriate microservice
3. Booking Service checks for customer information and makes synchronous calls to Inventory Service
4. Booking Service publishes events to Kafka queue (async)
5. Order Service consumes events from the queue
6. Order Service saves order information to the database
7. Inventory Service updates availability

## Project Structure

### API Gateway
```
apigateway/
├── .idea/                  # IntelliJ IDEA configuration
├── .mvn/                   # Maven wrapper
├── src/
│   ├── main/
│   │   ├── java/com.mdharr.apigateway/
│   │   │   ├── config/     # Configuration classes including SecurityConfig
│   │   │   ├── route/      # Gateway routing configuration
│   │   │   └── ApigatewayApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── target/
├── pom.xml                 # Maven project configuration
├── .gitattributes
└── .gitignore
```

### Booking Service
```
bookingservice/
├── .idea/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com.mdharr.bookingservice/
│   │   │   ├── client/     # Service clients (e.g., InventoryServiceClient)
│   │   │   ├── config/     # Configuration classes (e.g., OpenApiConfig)
│   │   │   ├── controller/ # REST controllers (e.g., BookingController)
│   │   │   ├── entity/     # JPA entities (e.g., Customer)
│   │   │   ├── event/      # Kafka event classes (e.g., BookingEvent)
│   │   │   ├── repository/ # Spring Data repositories
│   │   │   ├── request/    # API request models
│   │   │   ├── response/   # API response models
│   │   │   ├── service/    # Business logic implementation
│   │   │   └── BookingserviceApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── target/
└── pom.xml
```

### Inventory Service
```
inventoryservice/
├── .idea/
├── .mvn/
├── docker/
│   ├── keycloak/          # Keycloak configuration
│   └── mysql/             # MySQL initialization scripts with Flyway migrations
├── src/
│   ├── main/
│   │   ├── java/com.mdharr.inventoryservice/
│   │   │   ├── config/     # Configuration classes (e.g., OpenApiConfig)
│   │   │   ├── controller/ # REST controllers (e.g., InventoryController)
│   │   │   ├── entity/     # JPA entities (e.g., Event, Venue)
│   │   │   ├── playground/ # Testing/development classes
│   │   │   ├── repository/ # Spring Data repositories
│   │   │   ├── response/   # API response models
│   │   │   ├── service/    # Business logic implementation
│   │   │   └── InventoryserviceApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── target/
└── pom.xml
```

### Order Service
```
orderservice/
├── .idea/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com.mdharr/
│   │   │   ├── bookingservice.event/ # Shared event classes with Booking Service
│   │   │   ├── orderservice/
│   │   │   │   ├── client/    # Service clients
│   │   │   │   ├── entity/    # JPA entities (e.g., Order)
│   │   │   │   ├── repository/# Spring Data repositories
│   │   │   │   ├── service/   # Business logic implementation
│   │   │   │   └── OrderserviceApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
├── target/
└── pom.xml
```

## Installation and Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose
- Kafka
- MySQL

### Building and Running

1. Clone the repository:
   ```
   git clone https://github.com/mdharr/TicketingProject.git
   cd TicketingProject
   ```

2. Start the infrastructure services (Keycloak, MySQL, Kafka):
   ```
   docker-compose up -d
   ```

3. Build and run each microservice:

   **Inventory Service**
   ```
   cd inventoryservice
   mvn clean install
   mvn spring-boot:run
   ```
   
   **Booking Service**
   ```
   cd bookingservice
   mvn clean install
   mvn spring-boot:run
   ```
   
   **Order Service**
   ```
   cd orderservice
   mvn clean install
   mvn spring-boot:run
   ```
   
   **API Gateway** (start this last, after other services are running)
   ```
   cd apigateway
   mvn clean install
   mvn spring-boot:run
   ```

4. The API Gateway will be available at `http://localhost:8080`
5. Swagger UI documentation is available at:
   - API Gateway: `http://localhost:8080/swagger-ui.html`
   - Booking Service: `http://localhost:8081/swagger-ui.html`
   - Inventory Service: `http://localhost:8082/swagger-ui.html`
   - Order Service: `http://localhost:8083/swagger-ui.html`

## Environment Setup

### Keycloak Setup

Keycloak is used for authentication and authorization. The Docker configuration includes Keycloak setup with predefined realms for service security.

### Kafka Setup

Kafka is used for asynchronous messaging. The following topics are required:
- `booking-events` - Used for communication between Booking Service and Order Service

### Database Schema

Each service has its own database schema:
- `inventory_db` - Stores events, venues and available tickets
- `booking_db` - Stores customer information and booking requests
- `order_db` - Stores order information

## API Documentation

Each microservice provides its own Swagger documentation that describes available endpoints, request/response formats, and authentication requirements.

## Security

Authentication and authorization are handled by Keycloak. The API Gateway validates all incoming requests against Keycloak before routing them to the appropriate microservice.

## Database

Each microservice has its own database schema in MySQL, following the microservice architectural pattern of maintaining separate data stores.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

Project Link: [https://github.com/mdharr/TicketingProject](https://github.com/mdharr/TicketingProject)