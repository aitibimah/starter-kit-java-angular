# Spring Boot + Angular Product Management Application

A full-stack application for managing products, built with Spring Boot 3 and Angular 17. This application demonstrates best practices in modern web development, including security, performance optimization, and maintainable code structure.

## Features

- 🔐 Secure authentication with JWT
- 📦 Product management (CRUD operations)
- 📤 File upload capabilities
- 📊 Data validation and error handling
- 📱 Responsive design
- 🔍 Advanced search and filtering
- 📈 Performance monitoring
- 🧪 Comprehensive test coverage

## Tech Stack

### Backend
- Spring Boot 3.2.3
- Spring Security with JWT
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit 5
- Swagger/OpenAPI

### Frontend
- Angular 17
- Angular Material
- RxJS
- NgRx (State Management)
- Jest
- Cypress

## Prerequisites

- Docker and Docker Compose
- JDK 17
- Node.js 18+
- Maven 3.8+
- Git
- PostgreSQL (if running without Docker)

## Getting Started

### Using Docker Compose (Recommended)

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/starter-kit-java-angular.git
   cd starter-kit-java-angular
   ```

2. Create a `.env` file in the root directory:
   ```env
   # Database Configuration
   DB_USER=postgres
   DB_PASSWORD=postgres
   DB_NAME=product_management
   DB_PORT=5432

   # JWT Configuration
   JWT_SECRET=your-256-bit-secret
   JWT_EXPIRATION=86400000

   # Application Configuration
   SPRING_PROFILES_ACTIVE=dev
   SERVER_PORT=8080
   ```

3. Start the application:
   ```bash
   docker compose up -d
   ```

4. Access the application:
   - Frontend: http://localhost:4200
   - Backend API: http://localhost:8080/api
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - Database: localhost:5432

### Manual Setup

#### Backend

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/product_management
   spring.datasource.username=${DB_USER}
   spring.datasource.password=${DB_PASSWORD}
   ```

3. Build and run the application:
   ```bash
   ./mvnw clean install
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

3. Configure environment variables in `src/environments/environment.ts`:
   ```typescript
   export const environment = {
     production: false,
     apiUrl: 'http://localhost:8080/api'
   };
   ```

4. Start the development server:
   ```bash
   npm start
   ```

## Development

### Code Style

- Backend follows Google Java Style Guide
- Frontend follows Angular Style Guide
- Use Prettier for code formatting
- ESLint for TypeScript/JavaScript linting

### Git Workflow

1. Create a feature branch from `develop`:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. Make your changes and commit:
   ```bash
   git commit -m "feat: add new feature"
   ```

3. Push to remote:
   ```bash
   git push origin feature/your-feature-name
   ```

4. Create a Pull Request to `develop`

### Testing

#### Backend Tests
```bash
cd backend
./mvnw test
```

#### Frontend Tests
```bash
cd frontend
npm test
```

#### E2E Tests
```bash
cd frontend
npm run e2e
```

## Deployment

### CI/CD Pipeline

The project uses GitHub Actions for continuous integration and deployment. The pipeline includes:

1. **Build and Test**
   - Builds and tests the backend with Maven
   - Builds and tests the frontend with npm
   - Runs linting checks
   - Generates code coverage reports

2. **Security Scan**
   - Dependency vulnerability check
   - Code security analysis
   - Container security scan

3. **Build and Push Docker Images**
   - Builds Docker images for both backend and frontend
   - Pushes images to GitHub Container Registry
   - Tags images with version and latest

4. **Deployment**
   - Staging: Deploys to staging environment on merge to main
   - Production: Deploys to production environment on tag creation
   - Automated rollback on failure

### Required GitHub Secrets

- `DB_USER`: Database username
- `DB_PASSWORD`: Database password
- `JWT_SECRET`: JWT signing key
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
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/      # Java source code
│   │   │   └── resources/ # Configuration files
│   │   └── test/          # Test files
│   ├── Dockerfile         # Backend Dockerfile
│   └── pom.xml           # Maven configuration
├── frontend/              # Angular application
│   ├── src/
│   │   ├── app/          # Application code
│   │   ├── assets/       # Static assets
│   │   └── environments/ # Environment configs
│   ├── Dockerfile        # Frontend Dockerfile
│   └── package.json      # npm configuration
├── .github/              # GitHub Actions workflows
├── docker-compose.yml    # Docker Compose configuration
└── README.md            # Project documentation
```

## API Documentation

The API documentation is available through Swagger UI at `http://localhost:8080/swagger-ui.html` when running the application.

Key endpoints:
- `POST /api/auth/login`: User authentication
- `GET /api/products`: List all products
- `POST /api/products`: Create a new product
- `PUT /api/products/{id}`: Update a product
- `DELETE /api/products/{id}`: Delete a product

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

Please follow our [Contributing Guidelines](CONTRIBUTING.md) for more details.

## Troubleshooting

Common issues and their solutions:

1. **Database Connection Issues**
   - Check if PostgreSQL is running
   - Verify database credentials in `.env`
   - Ensure database exists

2. **JWT Authentication Issues**
   - Verify JWT_SECRET in environment
   - Check token expiration
   - Ensure proper token format

3. **Build Issues**
   - Clear Maven cache: `./mvnw clean`
   - Clear npm cache: `npm cache clean --force`
   - Delete node_modules: `rm -rf node_modules`

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support, please:
1. Check the [documentation](docs/)
2. Search [existing issues](https://github.com/yourusername/starter-kit-java-angular/issues)
3. Create a new issue if needed

## Acknowledgments

- Spring Boot Team
- Angular Team
- All contributors