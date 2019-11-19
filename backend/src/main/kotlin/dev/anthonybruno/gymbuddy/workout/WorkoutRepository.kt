package dev.anthonybruno.gymbuddy.workout

import dev.anthonybruno.gymbuddy.common.Repository

import java.sql.*
import java.time.Instant
import java.util.ArrayList

class WorkoutRepository : Repository("workouts") {


    fun getWorkouts(userId: Long): List<Workout> {
        val workouts = ArrayList<Workout>()
        db.getConnection().use { conn ->
            conn.prepareStatement("SELECT * FROM $tableName WHERE user_id = ?").use { statement ->
                statement.setLong(1, userId)
                statement.executeQuery().use { resultSet ->
                    while (resultSet.next()) {
                        workouts.add(mapWorkoutFromResultSet(resultSet))
                    }
                }
                return workouts
            }
        }
    }

    fun getWorkout(workoutId: Int): Workout? {
        db.getConnection().use { conn ->
            conn.prepareStatement("SELECT * FROM $tableName WHERE id = ?").use { statement ->
                statement.setInt(1, workoutId)
                statement.executeQuery().use { resultSet ->
                    return if (resultSet.next()) {
                        mapWorkoutFromResultSet(resultSet)
                    } else {
                        null
                    }
                }
            }
        }

    }

    fun addWorkout(userId: Long, workout: AddWorkout): Workout {
        val workoutId: Int
        val conn = db.getConnection();
        conn.autoCommit = false;
        try {
            workoutId = addWorkoutToDb(conn, userId, workout)
            for (exercise in workout.exercises) {
                 addWorkoutExerciseToDb(conn, workoutId, exercise)
            }
            conn.commit()
        } catch (e: SQLException) {
            conn.rollback()
            throw e
        } finally {
            conn.autoCommit = false
            conn.close()
        }
        return getWorkout(workoutId)!!
    }

    private fun addWorkoutExerciseToDb(conn: Connection, workoutId: Int, exercise: AddExercise) {
        conn.prepareStatement("INSERT INTO workout_exercises(workout_id, exercise_id, sets, reps, weight) VALUES (?,?,?,?,?)").use { statement ->
            statement.setInt(1, workoutId)
            statement.setInt(2, exercise.id)
            statement.setInt(3, exercise.sets)
            statement.setInt(4, exercise.reps)
            if (exercise.weight != null) {
                statement.setInt(5, exercise.weight)
            } else {
                statement.setInt(5, 0) //TODO: Should be null here
            }
            statement.execute()
        }

    }

    private fun addWorkoutToDb(conn: Connection, userId: Long, workout: AddWorkout): Int {
        conn.prepareStatement("INSERT INTO $tableName(user_id, title, description, date) VALUES (?,?,?,?) RETURNING id").use { statement ->
            statement.setLong(1, userId)
            statement.setString(2, workout.title)
            statement.setString(3, workout.description)
            statement.setTimestamp(4, Timestamp.from(Instant.now()))
            val result = statement.executeQuery()
            return if (result.next()) {
                result.getInt(1)
            } else {
                -1;
            }
        }
    }

    private fun mapWorkoutFromResultSet(resultSet: ResultSet): Workout {
        return Workout(resultSet.getInt("id"),
                resultSet.getTimestamp("date").toInstant(),
                resultSet.getString("title"),
                resultSet.getString("description"),
                listOf())
    }
}
