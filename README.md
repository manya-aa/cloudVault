# CloudVault ☁️📦
Cloud-Native Inventory Management System

CloudVault is a backend-focused inventory management system built using Spring Boot. It simulates real-world production-grade backend engineering with layered architecture, caching, security, testing, containerization, and CI/CD automation.

---

## 🚀 Project Overview

CloudVault manages products, categories, suppliers, and stock entries. The goal is to replicate how real production systems are designed and deployed with proper engineering practices, not just CRUD APIs.

---

## 📊 Project Status

- ✅ Phase 1: Spring Boot setup + CRUD APIs
- ✅ Phase 2: Database design + schema planning
- ✅ Phase 3: Advanced REST APIs + filtering + Redis caching
- ✅ Phase 4: Security (Spring Security + rate limiting + environment-based secrets)
- 🧪 Phase 5: Unit testing (JUnit + Mockito) — in progress
- 🐳 Phase 6: Dockerized application
- ⚙️ Phase 7: CI/CD pipeline (GitHub Actions → Docker Hub → EC2 deployment)

---

## 🧱 Tech Stack

Java 17, Spring Boot 3, Spring Data JPA (Hibernate), MySQL, Redis, Spring Security, JUnit 5, Mockito, Docker, GitHub Actions, Maven

---

## 🏗️ System Architecture

Client → Controller → Service → Repository → Database

Extended architecture layers:
- DTO layer for API separation
- Global exception handling
- Redis caching layer (cache-aside strategy)
- Security filter chain
- CI/CD automation pipeline

---

## 📁 Project Structure

src/main/java/com/project/cloudInventory
│
├── controller        # REST APIs
├── service           # Business logic layer
├── repository        # Database access layer (JPA)
├── entity            # Database models
├── dto               # Data transfer objects
├── configuration     # Configurations (Redis, Security, etc.)
├── security          # Spring Security setup
└── exception         # Global exception handling

---

## 📦 Core Domain Model

Entities:
- Product
- Category
- Supplier
- StockEntry

Relationships:
- Product → Category (Many-to-One)
- Product → Supplier (Many-to-One)
- StockEntry → Product (Many-to-One)

---

## ⚙️ Features

### Product Management
- Create, read, update, delete products
- Pagination support
- Dynamic filtering (category, supplier, price range, stock level)

### Redis Caching
- Cache product listings using TTL
- Cache-aside strategy implementation
- Cache eviction on create/update/delete operations

### Security
- Spring Security applied to all APIs
- Rate limiting using Bucket4j
- Environment variable-based configuration
- No hardcoded secrets

### Testing
- Unit tests using JUnit 5 + Mockito
- Service layer testing
- Validation and exception tests

### Docker
- Multi-stage Docker build
- Lightweight runtime image
- Docker Compose for local setup (app + MySQL + Redis)

### CI/CD Pipeline
- Automated testing on every push
- Docker image build and push to Docker Hub (main branch)
- Automatic EC2 deployment via SSH
- Environment variables injected securely at runtime

---

## 🚀 How to Run Locally

git clone <repo-url>
cd cloudvault

./mvnw clean install
./mvnw spring-boot:run

Application runs at:
http://localhost:8080

---

## 🐳 Run with Docker

docker build -t cloudvault .
docker run -p 8080:8080 cloudvault

---

## 🔁 CI/CD Pipeline (GitHub Actions)

Pipeline flow:
1. Run unit tests (mvn test)
2. Build JAR file
3. Build Docker image
4. Push image to Docker Hub
5. SSH into EC2 instance
6. Pull latest image
7. Restart container with updated version

---

## 🔐 Environment Variables

DB_URL=
DB_USERNAME=
DB_PASSWORD=
REDIS_HOST=
REDIS_PORT=

---

## 🧠 Design Principles

- Layered architecture
- DTO vs Entity separation
- Cache-aside pattern
- Stateless REST APIs
- Secure configuration using environment variables
- CI/CD-driven deployment workflow

---

## 🚧 Future Improvements

- JWT authentication and role-based access control
- Testcontainers integration (real DB + Redis testing)
- Centralized logging and monitoring
- AWS production-grade deployment (RDS + EC2 scaling)
- Frontend dashboard (React / Next.js)

---

## 👨‍💻 Author

CloudVault is a backend engineering and cloud-native project built to demonstrate production-level Spring Boot architecture, DevOps workflows, and scalable system design.
