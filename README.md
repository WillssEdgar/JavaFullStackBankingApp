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



## Frontend Routing

The frontend application uses React Router for routing. The following are the available routes:

- `/`: Home
- `/login`: Login
- `/register`: Register
- `/accounts`: Account management
- `/transactions`: Transaction history

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
