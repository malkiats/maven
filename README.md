# Maven Java Demo Application

A Spring Boot-based REST API application demonstrating CI/CD pipeline integration for the CICD-Lab project.

## Overview

This is a complete Maven Java application featuring:
- Spring Boot REST API endpoints
- Unit tests with JUnit
- Docker containerization (multi-stage build)
- Jenkins CI/CD pipeline integration
- Kubernetes deployment manifest
- Harbor container registry integration

## Project Structure

```
demo-java-app/
├── pom.xml                 # Maven project configuration
├── Dockerfile              # Multi-stage Docker build
├── Jenkinsfile            # Jenkins CI/CD pipeline
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── App.java         # Main application entry point
│   │   │   ├── Controller.java  # REST API endpoints
│   │   │   └── Service.java     # Business logic
│   │   └── resources/
│   │       └── application.properties  # Spring Boot config
│   └── test/
│       └── java/com/
│           └── AppTest.java     # Unit tests
└── README.md              # This file
```

## Prerequisites

- Java 11+
- Maven 3.8.1+
- Docker (for containerization)
- kubectl (for Kubernetes deployment)

## Building Locally

### 1. Clone the Repository

```bash
git clone https://github.com/malkiats/maven.git
cd maven
```

### 2. Build with Maven

```bash
# Clean and build the project
mvn clean package

# Run tests
mvn test

# Build without tests (faster)
mvn clean package -DskipTests
```

### 3. Run the Application Locally

```bash
# Method 1: Using Maven
mvn spring-boot:run

# Method 2: Using Java directly
java -jar target/maven-java-app-1.0.0.jar
```

The application will start on `http://localhost:8080`

## API Endpoints

Once the application is running, you can test these endpoints:

### Health Check
```bash
curl http://localhost:8080/api/health
```
Response:
```json
{
  "status": "UP",
  "application": "Maven Java Demo App",
  "version": "1.0.0"
}
```

### Welcome
```bash
curl http://localhost:8080/api/
```

### Greeting
```bash
curl "http://localhost:8080/api/greet?name=Alice"
```

### Status
```bash
curl http://localhost:8080/api/status
```

### Calculator - Addition
```bash
curl "http://localhost:8080/api/calc/add?a=5&b=3"
```
Response:
```json
{
  "operation": "addition",
  "a": 5,
  "b": 3,
  "result": 8
}
```

### Calculator - Multiplication
```bash
curl "http://localhost:8080/api/calc/multiply?a=5&b=3"
```

### Echo (POST)
```bash
curl -X POST http://localhost:8080/api/echo -d "Hello World"
```

## Docker Build

### Build Docker Image

```bash
docker build -t maven-java-app:1.0 .
```

### Run Docker Container

```bash
docker run -p 8080:8080 maven-java-app:1.0
```

### Push to Harbor Registry

```bash
# Configure Harbor credentials in docker daemon config
# Edit /etc/docker/daemon.json with:
# {
#     "insecure-registries": ["192.168.101.11"]
# }

# Log in to Harbor
docker login 192.168.101.11

# Tag and push
docker tag maven-java-app:1.0 192.168.101.11/java-app/maven-java-app:1.0
docker push 192.168.101.11/java-app/maven-java-app:1.0
```

## Kubernetes Deployment

### Deploy to Kubernetes Cluster

```bash
# Create namespace
kubectl create namespace java-apps

# Apply deployment
kubectl apply -f k8s-deployment.yaml

# Check status
kubectl get pods -n java-apps
kubectl get svc -n java-apps
```

### Get Service Endpoint

```bash
kubectl get svc maven-app-service -n java-apps
```

## Jenkins CI/CD Pipeline

The `Jenkinsfile` defines a complete CI/CD pipeline with these stages:

1. **Checkout** - Clone repository
2. **Build** - Maven clean package
3. **Unit Tests** - Run JUnit tests
4. **Code Analysis** - Code quality checks
5. **Build Docker Image** - Create container image
6. **Push to Harbor** - Push to registry
7. **Deploy to Kubernetes** - Deploy to K8s cluster

### Creating Jenkins Job

1. Go to Jenkins: `http://192.168.101.13:8080`
2. Click "New Item"
3. Enter name: `Maven-Java-App`
4. Select: Pipeline
5. Configure:
   - Definition: Pipeline script from SCM
   - SCM: Git
   - Repository URL: `https://github.com/malkiats/maven.git`
   - Script Path: `Jenkinsfile`
6. Click Build

## Running Unit Tests

```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=AppTest

# Run with coverage (if configured)
mvn test jacoco:report
```

## Troubleshooting

### Maven Build Issues

```bash
# Clear Maven cache
rm -rf ~/.m2/repository

# Update dependencies
mvn clean dependency:resolve

# Check Java version
java -version
```

### Docker Build Issues

```bash
# Check Docker daemon
docker ps

# View build logs
docker build -t app:1.0 . --progress=plain

# Remove dangling images
docker image prune -a
```

### Kubernetes Issues

```bash
# Check pod logs
kubectl logs -f pod-name -n java-apps

# Describe pod for events
kubectl describe pod pod-name -n java-apps

# Check service
kubectl describe svc maven-app-service -n java-apps
```

## Performance & Resource Requirements

- **Memory**: 256MB minimum (requests), 512MB maximum (limits)
- **CPU**: 100m minimum (requests), 500m maximum (limits)
- **Build Time**: ~2-3 minutes (with dependencies cached)
- **Docker Image Size**: ~150MB

## Security Considerations

1. Application runs as non-root user in Docker
2. Health checks enabled for container monitoring
3. Resource limits configured in Kubernetes
4. Input validation on API endpoints

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make changes and add tests
4. Submit a pull request

## License

This project is for educational purposes in the CICD-Lab.

## Support

For issues or questions:
- Check the [PHASE-2-Hands-On-Lab.md](../PHASE-2-Hands-On-Lab.md)
- Review application logs
- Check Kubernetes pod events

---

**Version**: 1.0.0  
**Last Updated**: 2026-07-24
