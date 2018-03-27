# Gym Buddy
A web based application to track workouts

## Goals
- Be able to enter workouts completed
- See past history of workouts 
- User sign up/authentication/etc.
- Use minimal frameworks on both backend and frontend (no Spring here!)

## API

### Authentication

#### /auth/login
POST
```json
{
  "username": "username here",
  "password": "password here"
}
```

#### /auth/logout
POST

### /auth/logcheck
POST

### Endpoints
**All urls are prefixed with `/api/v1`**

#### /user/{userId}

##### GET
Returns user with provided id details.

##### PUT
Edit user with provided id.

##### DELETE
Delete user with provided id.

### /user/{userId}/workout

#### GET
Returns workouts of user with the provided id.

#### POST
Add a new workout.

### /user/{userId}/workout/{workoutId}

#### GET
Returns specific workout.

#### PUT
Changes specific workout.

#### DELETE
Delete's specific workout.

