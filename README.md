# Gym Buddy
A web based application to track workouts

## Goals
- Be able to enter workouts completed
- See past history of workouts 
- User sign up/authentication/etc.
- Use minimal frameworks on both backend and frontend (no Spring here!)

## Pre-reqs
- Java 11
- Postgres 11+

## Building
1. In project root directory: `./mvnw package`
2. Run with `java -jar backend/target/backend-1.0-SNAPSHOT.jar`

## DB
### Migrating DB:
In project root directory: 

`./mvnw flyway:migrate -pl backend -Dflyway.url="" -Dflyway.user="" -Dflyway.password=""`

### Loading exercises.csv
In `psql`:

`\copy exercises(name,description,main_muscle) FROM 'exercises.csv' WITH DELIMITER ',' CSV HEADER;`

## Built With
- [Javalin](https://javalin.io/) - Backend web framework
- [Flyway](https://flywaydb.org/) - Database migrations
- [TypeScript](https://www.typescriptlang.org/) - Frontend language
- [React](https://reactjs.org/) - Frontend framework

## API

### Endpoints
**All urls are prefixed with `/api/v1`**


