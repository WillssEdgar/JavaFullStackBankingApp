# Banking Application

This is a full-stack banking application developed using Spring Boot for the backend, PostgreSQL for the database, and React with TypeScript for the frontend.

## Features

- User registration and authentication
- Account management (Create, Update, Delete)
- Transaction history
- Balance inquiry

## Technologies Used

### Backend
- Java 21
- Spring Boot 3.0.0
- Spring Security
- PostgreSQL
- Maven

### Frontend
- React
- TypeScript
- React Router
- Axios

### Other Technologies
- Postman
- Figma

## Setup Instructions

### Backend Setup

1. Clone the repository:

    ```
    git clone <repository-url>
    ```

2. Navigate to the backend directory:

    ```
    cd backend
    ```

3. Configure PostgreSQL database settings in `src/main/resources/application.properties`.

4. Run the backend application:

    ```
    ./mvnw spring-boot:run
    ```

    The backend server should start running on `http://localhost:8080`.

### Frontend Setup

1. Navigate to the frontend directory:

    ```
    cd frontend
    ```

2. Install dependencies:

    ```
    npm install
    ```

3. Run the frontend application:

    ```
    npm run dev
    ```

    The frontend development server should start running on `http://localhost:5371`.

## API Endpoints

The following are the available API endpoints:
- `/login`
- `/register`
- `/accounts/transactions/deposit`
- `/accounts/transactions/withdrawal`
- `/accounts/transactions/transfer`
- `/accounts/addNewAccount`
- `/transactions/getTransactions`
- `/accounts/account`



## Frontend Routing

The frontend application uses React Router for routing. The following are the available routes:

- `/`: Home
- `/Login_Create`: Login
- `/Dashboard`: Dashboard
- `/Account`: Account Details


