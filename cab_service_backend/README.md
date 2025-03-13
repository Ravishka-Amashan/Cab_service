# Mega City Cab - Vehicle Registration System

This is a Spring Boot application for managing a cab service system. It provides a RESTful API for managing customers, vehicles, drivers, bookings, and bills.

## Features

- User authentication with JWT
- Customer management
- Vehicle management
- Driver management
- Booking management
- Bill generation and payment processing

## Technologies Used

### Backend
- Java 17
- Spring Boot 3.4.2
- Spring Security
- Spring Data MongoDB
- JWT for authentication
- Maven for dependency management

### Frontend
- React 19
- React Router 7
- Axios for API calls
- Styled Components for styling

## Prerequisites

- Java 17 or higher
- MongoDB 4.4 or higher
- Maven 3.6 or higher
- Node.js 18 or higher
- npm 9 or higher

## Setup and Installation

### Backend Setup

1. Clone the repository:
   ```
   git clone <repository-url>
   cd vehicleRegistration
   ```

2. Install MongoDB:
   - Download and install MongoDB from [MongoDB Download Center](https://www.mongodb.com/try/download/community)
   - Start MongoDB service

3. Configure the application:
   - Update `src/main/resources/application.properties` if needed

4. Build the application:
   ```
   mvn clean install
   ```

5. Run the application:
   ```
   mvn spring-boot:run
   ```

6. The backend API will be available at `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
   ```
   cd ../VehicleRegistrationUI/megacitycab-frontend
   ```

2. Install dependencies:
   ```
   npm install
   ```

3. Run the frontend application:
   ```
   npm start
   ```

4. The frontend application will be available at `http://localhost:3000`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new customer
- `POST /api/auth/login` - Login and get JWT token
- `POST /api/auth/admin/register` - Register a new admin
- `POST /api/auth/driver/register` - Register a new driver

### Customers
- `GET /api/customers` - Get all customers
- `GET /api/customers/{id}` - Get customer by ID
- `POST /api/customers` - Create a new customer
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer

### Vehicles
- `GET /api/vehicles` - Get all vehicles
- `GET /api/vehicles/{id}` - Get vehicle by ID
- `GET /api/vehicles/status/{status}` - Get vehicles by status
- `POST /api/vehicles` - Add a new vehicle
- `PUT /api/vehicles/{id}` - Update vehicle
- `PATCH /api/vehicles/{id}/status` - Update vehicle status
- `DELETE /api/vehicles/{id}` - Delete vehicle

### Drivers
- `GET /api/drivers` - Get all drivers
- `GET /api/drivers/{id}` - Get driver by ID
- `GET /api/drivers/status/{status}` - Get drivers by status
- `POST /api/drivers` - Add a new driver
- `PUT /api/drivers/{id}` - Update driver
- `PATCH /api/drivers/{id}/status` - Update driver status
- `PATCH /api/drivers/{id}/assign-vehicle` - Assign vehicle to driver
- `DELETE /api/drivers/{id}` - Delete driver

### Bookings
- `GET /api/bookings` - Get all bookings
- `GET /api/bookings/{id}` - Get booking by ID
- `GET /api/bookings/customer/{customerId}` - Get bookings by customer
- `GET /api/bookings/status/{status}` - Get bookings by status
- `POST /api/bookings` - Create a new booking
- `PUT /api/bookings/{id}` - Update booking
- `PATCH /api/bookings/{id}/status` - Update booking status
- `DELETE /api/bookings/{id}` - Delete booking

### Bills
- `GET /api/bills` - Get all bills
- `GET /api/bills/{id}` - Get bill by ID
- `GET /api/bills/booking/{bookingId}` - Get bill by booking ID
- `GET /api/bills/customer/{customerId}` - Get bills by customer
- `POST /api/bills` - Create a new bill
- `POST /api/bills/generate-from-booking/{bookingId}` - Generate bill from booking
- `PUT /api/bills/{id}` - Update bill
- `PATCH /api/bills/{id}/payment-status` - Update payment status
- `DELETE /api/bills/{id}` - Delete bill

## Security

The application uses JWT (JSON Web Token) for authentication. To access protected endpoints, you need to include the JWT token in the Authorization header:

```
Authorization: Bearer <token>
```

You can obtain a token by logging in with a valid username and password using the `/api/auth/login` endpoint.

## Testing

Run the tests using Maven:

```
mvn test
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.
