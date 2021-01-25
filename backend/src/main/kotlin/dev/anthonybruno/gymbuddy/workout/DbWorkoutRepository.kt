package dev.anthonybruno.gymbuddy.workout

import dev.anthonybruno.gymbuddy.db.Database
import dev.anthonybruno.gymbuddy.db.jooq.tables.WorkoutExercises.Companion.WORKOUT_EXERCISES
import dev.anthonybruno.gymbuddy.db.jooq.tables.Workouts.Companion.WORKOUTS
import dev.anthonybruno.gymbuddy.db.jooq.tables.references.EXERCISES
import dev.anthonybruno.gymbuddy.db.jooq.tables.references.USERS
import org.jooq.Condition
import org.jooq.DatePart
import org.jooq.impl.DSL
import org.jooq.impl.DSL.*
import java.lang.IllegalArgumentException
import java.lang.RuntimeException

import java.sql.*
import java.time.Instant
import java.time.YearMonth
import java.time.temporal.ChronoUnit

class DbWorkoutRepository(private val db: Database) : WorkoutRepository {

    private val tableName = "workouts"
    private val dbHelper = db.getHelper();

    private val userIdCondition = { userId: Int -> WORKOUTS.USER_ID.eq(userId) }
    private val workoutIdCondition = { workoutId: Int -> WORKOUTS.ID.eq(workoutId) }

    override fun getWorkouts(userId: Int): List<Workout> {
        return getWorkouts(userIdCondition(userId))
    }

    override fun getWorkout(workoutId: Int): Workout? {
        val workouts = getWorkouts(workoutIdCondition(workoutId))
        return if (workouts.isEmpty()) {
            null
        } else {
            workouts[0]
        }
    }

    private fun getWorkouts(vararg conditions: Condition): List<Workout> {
        val w = WORKOUTS
        val we = WORKOUT_EXERCISES
        val e = EXERCISES;
        val workouts = mutableMapOf<Workout, MutableList<WorkoutExerciseDto>>()
        db.jooq().select(w.ID, w.TITLE, w.DESCRIPTION, w.DATE, we.WEIGHT, we.SETS, we.REPS, e.NAME)
                .from(w)
                .leftJoin(we).on(w.ID.eq(we.WORKOUT_ID))
                .leftJoin(e).on(we.EXERCISE_ID.eq(e.ID))
                .where(*conditions)
                .fetch { record ->
                    val workout = Workout(record[w.ID]!!, record[w.DATE]!!.toInstant(), record[w.TITLE], record[w.DESCRIPTION], emptyList())
                    workouts.compute(workout) { _, list ->
                        // If workout has no exercises, every exercise column will be null. In this case, we can just
                        // return an empty list.
                        val name = record[e.NAME] ?: return@compute mutableListOf()

                        val workoutExerciseDto = WorkoutExerciseDto(name, record[we.WEIGHT], record[we.SETS]!!, record[we.REPS]!!)
                        if (list == null) {
                            mutableListOf(workoutExerciseDto)
                        } else {
                            list.add(workoutExerciseDto)
                            list
                        }
                    }
                }
        return workouts.toList().map {
            it.first.copy(exercises = it.second)
        }

    }

    override fun addWorkout(userId: Int, workout: AddWorkout): Workout {
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

    override fun getStats(userId: Int): WorkoutStats {
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
                 most_common_exercise AS (SELECT exercises.id, exercises.name, COUNT(*) as count
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
            """)
            val now = Instant.now()
            val thirtyDaysAgo = now.minus(30, ChronoUnit.DAYS)
            statement.setInt(1, userId)
            statement.setInt(2, userId)
            statement.setTimestamp(3, Timestamp.from(thirtyDaysAgo))
            statement.setInt(4, userId)
            statement
        }, { rs, _ ->
            WorkoutStats(rs.getTimestamp(1).toInstant(), rs.getString(3), rs.getInt(2))
        }) ?: WorkoutStats(null, "", 0)
    }

    override fun getWorkoutsPerMonth(userId: Int): List<WorkoutsOnMonth> {
        return dbHelper.query({
            it.prepareStatement("""
                 SELECT date_part('month', w.date AT TIME ZONE u.timezone) AS month, date_part('year', w.date AT TIME ZONE u.timezone) AS year, count(*) as workouts
                 FROM workouts w
                 LEFT JOIN users u on u.id = w.user_id
                 WHERE w.user_id = ?
                 GROUP BY month, year
                 ORDER BY year, month
            """).apply {
                setInt(1, userId)
            }
        }, { rs, rc -> WorkoutsOnMonth(YearMonth.of(rs.getInt("year"), rs.getInt("month")), rs.getInt("workouts")) });

        //TODO
//        val w = WORKOUTS.`as`("w")
//        val u = USERS.`as`("u")
//        // Need AT TIME ZONE but jooq does not support it!
//        // https://github.com/jOOQ/jOOQ/issues/7238
//        return db.jooq()
//            .select(field("date_part('month', w.date AT TIME ZONE u.timezone)").`as`("month"),
//                field("date_part('year', w.date AT TIME ZONE u.timezone)").`as`("year"),
//                count().`as`("workouts"))
//            .from(w)
//            .leftJoin(u).on(w.ID.eq(w.USER_ID))
//            .where(w.USER_ID.eq(userId))
//            .groupBy(field("month"), field("year"))
//            .orderBy(field("year"), field("month"))
//            .fetch {
//                val year = it.get("year", Int::class.java)
//                val month = it.get("month", Int::class.java)
//                WorkoutsOnMonth(YearMonth.of(year, month), it.value3())
//            }
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

    private fun addWorkoutToDb(conn: Connection, userId: Int, workout: AddWorkout): Int {
        conn.prepareStatement("""
            INSERT INTO $tableName(user_id, title, description, date, timezone) 
            SELECT ?, ?, ?, ?, CASE WHEN ? IS NOT NULL THEN ?
                                    ELSE u.timezone END
            FROM users u
            WHERE u.id = ?
            RETURNING id
            """).use { statement ->
            statement.setInt(1, userId)
            statement.setString(2, workout.title)
            statement.setString(3, workout.description)
            statement.setTimestamp(4, Timestamp.from(Instant.now()))
            val timezone = workout.timezone?.toString();
            statement.setString(5, timezone)
            statement.setString(6, timezone)
            statement.setInt(7, userId)
            val result = statement.executeQuery()
            return if (result.next()) {
                result.getInt(1)
            } else {
                throw RuntimeException("Failed to insert!")
            }
        }
    }

}
