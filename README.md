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
- Ranking system

### Secondary features
- Feature tags
- Managing hotel info
- Managing prices
- Write in/out guests for hotel administrator
- Approval of booking by hotel administrator
- Transportation options

### Advanced features
- Map integration
- Route planning

## Development

### Overview
Project is going to be developed using Spring Boot on the backend.
It also uses Modulith toolkit to ease transition to microservice architecture.
Selected build system is Maven as it's mature and well-established tool in the Java ecosystem.
Currently it uses H2 database and will use Postgres in the future. Here is the brief
overview of the modules that are currently implemented:

![Service Diagram](./backend/docs/service_diagram.png)

### Local
Run `./mvnw package` (with required environment variables from the .env.example file set).
Then run the resulting jar file using `java -jar target/crooodle-{VERSION}.jar`.
