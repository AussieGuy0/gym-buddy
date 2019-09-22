package dev.anthonybruno.gymbuddy.workout

import dev.anthonybruno.gymbuddy.common.Repository

import java.sql.*
import java.time.LocalDate
import java.util.ArrayList

class WorkoutRepository : Repository("workouts") {


    fun getWorkouts(userId: Int): List<Workout> {
        val workouts = ArrayList<Workout>()
        db.getConnection().use { conn ->
            conn.prepareStatement("SELECT * FROM $tableName WHERE user_id = ?").use { statement ->
                statement.setInt(1, userId)
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

    fun addWorkout(userId: Int, workout: Workout): Workout {
        var id = -1;
        db.getConnection().use { conn ->
            conn.prepareStatement("INSERT INTO $tableName(user_id, title, description, date) VALUES (?,?,?,?) RETURNING id").use { statement ->
                statement.setInt(1, userId)
                statement.setString(2, workout.title)
                statement.setString(3, workout.description)
                statement.setString(4, workout.date.toString())
                val result = statement.executeQuery()
                if (result.next()) {
                    id = result.getInt(0)
                }
            }
        }

        val workout = getWorkout(id)
        return workout!!
    }

    private fun mapWorkoutFromResultSet(resultSet: ResultSet): Workout {
        return Workout(resultSet.getInt("id"),
                LocalDate.parse(resultSet.getString("date")),
                resultSet.getString("title"),
                resultSet.getString("description"))
    }
}
