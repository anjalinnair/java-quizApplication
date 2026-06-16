# Quiz Application - Microservices Architecture

This repository contains a **Quiz Application** developed using **Spring Boot** and a **Microservices architecture**. The project demonstrates the implementation of a distributed system with multiple independent services, showcasing best practices for building scalable and maintainable applications.

---

## Services

### 1. Service Registry
- Acts as a centralized directory for all microservices.
- Enables efficient service discovery and communication.

### 2. API Gateway
- Serves as the single entry point for client requests.
- Routes requests to appropriate microservices.
- Enforces security constraints by validating incoming API requests using a custom authentication filter.

### 3. Question Service
- Manages the creation, storage, and retrieval of quiz questions.
- Provides endpoints for adding, updating, retrieving, and deleting questions.
- Employs strict DTO mappings to prevent database entities and correct answers from leaking to clients.

### 4. Quiz Service
- Orchestrates the quiz-taking process.
- Integrates with the Question Service to present questions to users and calculate scores.
- Handles user responses and maintains the flow of the quiz.

---

## Key Features
- **Microservices Architecture**  
  Each service is independently deployable and scalable, promoting flexibility and maintainability.

- **Service Discovery**  
  The Service Registry allows dynamic discovery of services, facilitating seamless communication between them.

- **Centralized Routing**  
  The API Gateway manages all incoming requests, directing them to the appropriate service and handling common concerns.

- **Security & Authentication**  
  Implemented global request authentication at the API Gateway using a Bearer token verification filter. Clients must provide the token in request headers to access routed endpoints.

- **Layered Clean Architecture**  
  Strictly separates the JPA Database Entities from Data Transfer Objects (DTOs) to enforce decoupling and prevent security exposure of correct answers.

---

## Security Configuration
All HTTP requests routed through the API Gateway (port `8080`) must include the following Authorization header:
*   **Header Key**: `Authorization`
*   **Header Value**: `Bearer secret-quiz-token-2026`

---

## Technology Stack
- **Spring Boot**: Framework for building the microservices.
- **Spring Cloud**: Tools for service discovery, routing (Spring Cloud Gateway), and cloud-native patterns (OpenFeign).
- **Java**: The primary programming language (Java 21).
- **PostgreSQL**: Database for persisting quiz definitions and questions.

---

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/anjalinnair/java-quizApplication.git
   ```

2. Navigate to each service directory and build the services:
   ```bash
   mvn clean install
   ```

3. Start the services in the following order:
   - Service Registry (Port `8761`)
   - API Gateway (Port `8080`)
   - Question Service (Port `8081`)
   - Quiz Service (Port `8090`)

4. Access the application through the API Gateway URL.

---

## Contact
If you have any questions, suggestions, or feedback, feel free to reach out:  
- **GitHub**: [@anjalinnair](https://github.com/anjalinnair)
- **Email**: anjalinnair13@gmail.com
- **LinkedIn**: [Anjali Nair](https://www.linkedin.com/in/anjalinnair13/)
