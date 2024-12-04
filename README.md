<<<<<<< HEAD
# Hackathon
=======
# Deployment Monitor

A monitoring system for tracking deployment status across OpenShift environments.

## Project Structure
- `frontend/`: Angular frontend application
- `backend/`: Spring Boot backend application

## Prerequisites
- Node.js v20.10.0
- npm 10.2.3
- Maven 3.9.9
- Java 17.0.13
- Angular CLI 18.2.2

## Development Setup

### Backend
1. Set environment variables:
   ```bash
   export OPENSHIFT_USERNAME=your-username
   export OPENSHIFT_PASSWORD=your-password
   ```
2. Navigate to backend directory:
   ```bash
   cd backend
   mvn spring-boot:run
   ```

### Frontend
1. Navigate to frontend directory:
   ```bash
   cd frontend
   npm install
   ng serve
   ```

## Building for Production
### Backend 
>>>>>>> 2648ea3 (initial commit)
