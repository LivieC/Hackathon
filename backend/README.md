# Deployment Monitor Backend

Spring Boot application for monitoring OpenShift deployments.

## Project Structure
```
src/main/java/com/monitor/
├── config/                 # Configuration classes
│   ├── OpenshiftConfig.java
│   └── WebConfig.java
├── controller/            # REST controllers
│   └── DeploymentController.java
├── model/                # Domain models
│   └── DeploymentStatus.java
├── service/              # Business logic
│   └── DeploymentMonitorService.java
└── DeploymentMonitorApplication.java
```

## Configuration
Set the following environment variables:
- OPENSHIFT_USERNAME
- OPENSHIFT_PASSWORD

## Build
```bash
mvn clean package
```

## Run
```bash
mvn spring-boot:run
``` 