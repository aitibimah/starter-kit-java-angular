# Spring Boot + Angular Product Management Application

A full-stack application for managing products, built with Spring Boot 3 and Angular 17.

## Prerequisites

- Docker and Docker Compose
- JDK 17
- Node.js 18
- Maven
- Git

## Local Development

### Using Docker Compose (Recommended)

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/starter-kit-java-angular.git
   cd starter-kit-java-angular
   ```

2. Create a `.env` file in the root directory:
   ```bash
   DB_USER=postgres
   DB_PASSWORD=postgres
   ```

3. Start the application:
   ```bash
   docker compose up -d
   ```

4. Access the application:
   - Frontend: http://localhost
   - Backend API: http://localhost:8080/api
   - Swagger UI: http://localhost:8080/swagger-ui.html

### Manual Setup

#### Backend

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Build and run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

#### Frontend

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

## Testing

### Backend Tests
```bash
cd backend
./mvnw test
```

### Frontend Tests
```bash
cd frontend
npm test
```

## CI/CD Pipeline

The project uses GitHub Actions for continuous integration and deployment. The pipeline includes:

1. **Build and Test**
   - Builds and tests the backend with Maven
   - Builds and tests the frontend with npm
   - Runs linting checks

2. **Build and Push Docker Images**
   - Builds Docker images for both backend and frontend
   - Pushes images to GitHub Container Registry

3. **Deployment**
   - Staging: Deploys to staging environment on merge to main
   - Production: Deploys to production environment on tag creation

### Required GitHub Secrets

- `DB_USER`: Database username
- `DB_PASSWORD`: Database password
- `STAGING_HOST`: Staging server hostname
- `STAGING_USER`: Staging server username
- `STAGING_SSH_KEY`: SSH private key for staging server
- `PROD_HOST`: Production server hostname
- `PROD_USER`: Production server username
- `PROD_SSH_KEY`: SSH private key for production server

## Project Structure

```
.
├── backend/                 # Spring Boot application
│   ├── src/                # Source code
│   ├── Dockerfile          # Backend Dockerfile
│   └── pom.xml            # Maven configuration
├── frontend/               # Angular application
│   ├── src/               # Source code
│   ├── Dockerfile         # Frontend Dockerfile
│   └── package.json       # npm configuration
├── .github/               # GitHub Actions workflows
├── docker-compose.yml     # Docker Compose configuration
└── README.md             # Project documentation
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.