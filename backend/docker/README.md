# Docker Development Environment

## Overview

This project uses Docker as the primary development environment.

The goal is to provide a consistent environment for every developer without requiring Java or Maven to be installed on the host machine.

The backend application runs entirely inside an Ubuntu container.

---

# Project Structure

```text
backend/
├── docker/
│   └── Dockerfile
│
├── docker-compose-local.yml
├── pom.xml
├── src/
└── ...
```

---

# Development Stack

| Component   | Version            |
| ----------- | ------------------ |
| Ubuntu      | 24.04 LTS          |
| Java        | Eclipse Temurin 25 |
| Maven       | 3.9.16             |
| PostgreSQL  | 16                 |
| Spring Boot | 4.x                |

---

# Design Philosophy

This Docker environment follows these principles:

* Ubuntu is used as the base image.
* Java is installed manually from Eclipse Temurin.
* Maven is installed manually from Apache.
* All important versions are configurable using `ARG`.
* Docker assets are separated from application source code.
* Development should not require Java or Maven on the host machine.

---

# Docker Volumes

## Maven Repository

```yaml
maven-repository:/root/.m2
```

Purpose:

* Cache Maven dependencies.
* Avoid downloading dependencies every time the container is recreated.
* Improve development performance.

---

## PostgreSQL Data

```yaml
postgres-data:/var/lib/postgresql/data
```

Purpose:

* Persist database data between container restarts.

---

# Start Development Environment

Build images and start containers.

```bash
docker compose -f docker-compose-local.yml up --build -d
```

---

# Stop Environment

```bash
docker compose -f docker-compose-local.yml down
```

---

# Enter Backend Container

```bash
docker exec -it backend bash
```

---

# Verify Java

```bash
java -version
```

---

# Verify Maven

```bash
mvn -version
```

---

# Run Spring Boot

```bash
mvn spring-boot:run
```

---

# API

Application

```
http://localhost:8080
```

Swagger

```
http://localhost:8080/swagger-ui/index.html
```

---

# PostgreSQL

Inside Docker network

```
Host : postgres
Port : 5432
```

From Host Machine

```
Host : localhost
Port : 5433
```

---

# Notes

* Containers communicate using the Docker network.
* The backend container connects to PostgreSQL using the container port (`5432`), not the host port.
* Maven dependencies are cached using a Docker volume.
* Source code is mounted into the container, allowing development without rebuilding the image for every code change.

---

# Future Improvements

* Spring Boot DevTools
* Remote debugging
* Multi-stage production Docker image
* Nginx reverse proxy
* Health checks
* Docker profiles
* Production Docker Compose
