package dev.anthonybruno.gymbuddy.workout

import dev.anthonybruno.gymbuddy.Server
import java.lang.RuntimeException

import java.sql.*
import java.time.Instant
import java.util.ArrayList

class DbWorkoutRepository : WorkoutRepository {

    private val tableName = "workouts"
    private val db = Server.DATABASE
    private val dbHelper = db.getHelper();

    override fun getWorkouts(userId: Long): List<Workout> {
        return dbHelper.query({
            val statement = it.prepareStatement("SELECT * FROM $tableName WHERE user_id = ?")
            statement.setLong(1, userId)
            statement
        }, { rs, _ ->
            mapWorkoutFromResultSet(rs)
        })
    }

    override fun getWorkout(workoutId: Int): Workout? {
        return dbHelper.queryOne({
            val statement = it.prepareStatement("SELECT * FROM $tableName WHERE id = ?")
            statement.setInt(1, workoutId)
            statement
        }, { rs, _ ->
            mapWorkoutFromResultSet(rs)
        })
    }

    override fun addWorkout(userId: Long, workout: AddWorkout): Workout {
        var workoutId = -1;
        dbHelper.runTransaction {
            workoutId = addWorkoutToDb(it, userId, workout)
            for (exercise in workout.exercises) {
                addWorkoutExerciseToDb(it, workoutId, exercise)
            }
        }
        if (workoutId == -1) {
            // should never happen
            throw RuntimeException("Workout not saved correctly?")
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
