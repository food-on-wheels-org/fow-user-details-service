# User Details Service

Handles user registration, authentication, and profile data.

---

## Overview
- Spring Boot REST API built with **AWS RDS (MySQL)**.  
- Endpoints for user management.
- Registered with Eureka Server for discovery.
- Exposed through Ingress and AWS ALB.
- Deployed on AWS EKS via ArgoCD.

---

## Tech Stack
Spring Boot, Spring Data JPA, AWS RDS, MySQL, Eureka Client, Docker, Kubernetes, Jenkins, SonarQube, ArgoCD

---

## Endpoints
| Method | Endpoint | Description |
|---------|-----------|-------------|
| POST | `/addUser` | Register a new user |
| GET | `/fetchUser/{id}` | Fetch user information |

---

## Deployment
Built and analyzed through Jenkins and SonarQube.
Docker image: `tejassrivathsa/user-details-service:latest`  
Deployed to AWS EKS using ArgoCD.
