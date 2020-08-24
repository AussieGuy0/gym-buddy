package dev.anthonybruno.gymbuddy.workout

import dev.anthonybruno.gymbuddy.Server
import dev.anthonybruno.gymbuddy.db.Database
import java.lang.RuntimeException

import java.sql.*
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.ArrayList

class DbWorkoutRepository(private val db: Database) : WorkoutRepository {

    private val tableName = "workouts"
    private val dbHelper = db.getHelper();

    override fun getWorkouts(userId: Long): List<Workout> {
        val query = """SELECT w.id, w.title, w.description, w.date, we.weight, we.sets, we.reps, e.name
                      |FROM $tableName as w
                      |LEFT JOIN workout_exercises as we ON w.id = we.workout_id
                      |LEFT JOIN exercises as e on we.exercise_id = e.id
                      |WHERE w.user_id = ?
                      |""".trimMargin()
        return dbHelper.query({
            val statement = it.prepareStatement(query)
            statement.setLong(1, userId)
            statement
        }, { rs ->
            val workouts = mutableMapOf<Workout, MutableList<WorkoutExerciseDto>>()
            while (rs.next()) {
                val workout = mapWorkoutFromResultSet(rs)
                workouts.compute(workout) { _, list ->
                    // If workout has no exercises, every exercise column will be null. In this case, we can just
                    // return an empty list.
                    val name = rs.getString("name")
                    if (name == null) {
                        return@compute mutableListOf()
                    }

                    val workoutExerciseDto = WorkoutExerciseDto(rs.getString("name"), rs.getInt("weight"), rs.getInt("sets"), rs.getInt("reps"))
                    if (list == null) {
                        mutableListOf(workoutExerciseDto)
                    } else {
                        list.add(workoutExerciseDto)
                        list
                    }
                }
            }
            workouts.toList().map {
                it.first.copy(exercises = it.second)
            }
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

    override fun getStats(userId: Long): WorkoutStats {
        //TODO: Handle case where user has no workouts
        return dbHelper.queryOne({
            val statement = it.prepareStatement("""
                WITH latest_workout_date AS (SELECT date
                  FROM $tableName
                  WHERE user_id = ?
                  ORDER BY date DESC
                  LIMIT 1
                 ),
                 workouts_last_30_days AS (SELECT COUNT(*)
                  FROM $tableName
                  WHERE user_id = ? AND date >= ? 
                 ),
                 most_common_exercise AS (SELECT exercises.id, exercises.name, COUNT(*)
                  FROM $tableName
                  LEFT JOIN workout_exercises ON $tableName.id = workout_exercises.workout_id
                  LEFT JOIN exercises ON workout_exercises.exercise_id = exercises.id
                  WHERE workouts.user_id = ?
                  GROUP BY exercises.id, exercises.name
                  ORDER BY count DESC
                  LIMIT 1
                 )
                 SELECT latest_workout_date.date, workouts_last_30_days.count, most_common_exercise.name
                 FROM latest_workout_date, workouts_last_30_days, most_common_exercise
            """.trimIndent())
            val now = Instant.now()
            val thirtyDaysAgo = now.minus(30, ChronoUnit.DAYS)
            statement.setLong(1, userId)
            statement.setLong(2, userId)
            statement.setTimestamp(3, Timestamp.from(thirtyDaysAgo))
            statement.setLong(4, userId)
            statement
        }, { rs, _ ->
            WorkoutStats(rs.getTimestamp(1).toInstant(), rs.getString(3), rs.getInt(2));
        }) ?: WorkoutStats(null, "", 0)
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

    private fun mapWorkoutFromResultSet(rs: ResultSet): Workout {
        return Workout(rs.getInt("id"),
                rs.getTimestamp("date").toInstant(),
                rs.getString("title"),
                rs.getString("description"),
                listOf())
    }
}
