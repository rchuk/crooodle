# Croodle

## About
Crooodle is a learning management system

### Basic features
- Authentication
- Role-based authorization
- Course enrollment
- Grading system
- User profiles
- Course page with files, links, assignments
- Task calendar or timeline

### Advanced features
- Educational assessment (multiple choice, etc.)

### Additional features
- Student randomizer
- Requests for deadline extension
- Section with certificate programs and current progress of the user there
- Automatic card generation from failed assessments
- Automatic card generation from lecturer's documents

## Development

### Local
Run `./mvnw package` (with required environment variables from the .env.example file set).
Then run the resulting jar file using `java -jar target/crooodle-{VERSION}.jar`.

### Docker
In console run:
`docker compose up`.

Database can be run separately using
`docker compose up backend_postgres`.
