# Crooodle

## About
Crooodle is an E-vacations system that helps connects users, hotels and transport companies

### Primary features
- Authentication
- Role-based authorization
- Room Booking
- Filter Hotels
- View Hotels
- View Rooms
- Leave and remove reviews
- Give and revert ranking

### Secondary features
- Feature tags
- Managing hotel info
- Managing prices
- Write in/out guests for hotel administrator
- Approval of booking by hotel administrator
- Request of hotel removal
- Transport Entity
- Transportation options

### Advanced features
- Map integration
- Reviews classification
- Route planning
- Advanced address-based filtering


## Development

### Overview
Project is going to be developed using Spring Boot on the backend.
Selected build system is Maven as it's mature and well-established tool in the Java ecosystem.
It also uses PostgresSQL database and can be deployed using Docker.

### Local
Run `./mvnw package` (with required environment variables from the .env.example file set).
Then run the resulting jar file using `java -jar target/crooodle-{VERSION}.jar`.
