# Hirezy

Hirezy is an AI-powered Job Portal system built with a modern microservices architecture. It aims to streamline the recruitment process by leveraging intelligent services for resumes, users, companies, and jobs.

## Architecture & Tech Stack

- **Backend Framework**: Java, Spring Boot
- **Database**: PostgreSQL
- **Containerization**: Docker & Docker Compose
- **Microservices**:
  - Resume Service
  - User Service
  - Company Service (and more)

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Docker and Docker Compose

### Running the Services Locally

1. **Start the Databases**
   The project uses PostgreSQL for its databases. You can spin up the required databases using Docker Compose:

   ```bash
   cd docker
   docker-compose up -d
   ```

2. **Build and Run the Microservices**
   Navigate to the respective service directories under `job-portal-system/services/` and run them using Maven:

   ```bash
   # Example: Running the User Service
   cd job-portal-system/services/job-portal-user-service
   mvn spring-boot:run
   ```

## Features

- **User Management**: Authentication, profile management, and role-based access.
- **Resume Management**: Create, update, and manage candidate resumes and work experience.
- **Microservices Architecture**: Modular and scalable system design.

## License

This project is licensed under the MIT License.
