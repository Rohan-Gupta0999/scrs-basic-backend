# Smart Course Recommendation System — Basic Backend

This is a deliberately small Spring Boot backend for the backend work assigned to Rohan.
It includes only:
- Spring Boot setup
- H2 database
- User registration and login
- Course list, course details and adding a course
- Course ratings (one rating per user per course; a later submission updates it)
- Course enrollment and cancellation

It does **not** include the Python recommendation algorithm, OS simulations, frontend, JWT, or production-grade authorization. Those are outside this minimal backend starter.

## Requirements
- JDK 21
- Maven 3.6.3 or later

Spring Boot 3.5.16 supports Java 17–25. Java 21 is used in this project.

## Run on macOS
Open Terminal in this folder and run:

```bash
java -version
mvn -version
mvn spring-boot:run
```

Then open:
- API base: `http://localhost:8080/api`
- H2 console: `http://localhost:8080/h2-console`

H2 console connection values:
- JDBC URL: `jdbc:h2:file:./data/scrsdb`
- User Name: `sa`
- Password: leave blank

The application creates four sample courses automatically on first run.

## API list

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/register` | Register a user |
| POST | `/api/auth/login` | Check login credentials |
| GET | `/api/courses` | List courses |
| GET | `/api/courses/{id}` | Course details |
| POST | `/api/courses` | Add a course |
| POST | `/api/courses/{courseId}/ratings` | Add or update a rating |
| GET | `/api/courses/{courseId}/ratings` | List ratings for a course |
| POST | `/api/courses/{courseId}/enroll` | Enroll a user |
| DELETE | `/api/courses/{courseId}/enroll?userId={userId}` | Cancel enrollment |
| GET | `/api/users/{userId}/enrollments` | List a user's enrollments |

## Quick test with curl

Register:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"fullName":"Rohan","email":"rohan@example.com","password":"hello123"}'
```

Login:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"rohan@example.com","password":"hello123"}'
```

List courses:

```bash
curl http://localhost:8080/api/courses
```

Enroll user 1 in course 1:

```bash
curl -X POST http://localhost:8080/api/courses/1/enroll \
  -H 'Content-Type: application/json' \
  -d '{"userId":1}'
```

Rate course 1 as user 1:

```bash
curl -X POST http://localhost:8080/api/courses/1/ratings \
  -H 'Content-Type: application/json' \
  -d '{"userId":1,"rating":5,"comment":"Useful course"}'
```

## Important demonstration limitation
For a basic classroom demo, login validates the password and returns the user's profile, but does not issue a token or create a session. Passwords are stored as BCrypt hashes, not plain text. The enrollment service uses a synchronized method suitable only for a single local application instance; it is not a production distributed-concurrency solution. Do not deploy this as a real public authentication system.
