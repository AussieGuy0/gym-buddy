package dev.anthonybruno.gymbuddy.workout

import dev.anthonybruno.gymbuddy.Server
import dev.anthonybruno.gymbuddy.util.ensureUserSignedIn
import io.javalin.http.Context
import java.sql.ResultSet
import java.util.ArrayList
import kotlin.system.exitProcess

class ExerciseController(private val exerciseService: ExerciseService = ExerciseService()) {

    fun getExercises(ctx: Context) {
        ensureUserSignedIn(ctx)
        ctx.json(exerciseService.getExercises())
    }

}

class ExerciseService(private val exerciseRepository: ExerciseRepository = ExerciseRepository()) {

    fun getExercises(): List<Exercise> {
        return exerciseRepository.getExercises();
    }

}

class ExerciseRepository {

    private val tableName = "exercises"
    private val db = Server.DATABASE

    fun getExercises(): List<Exercise> {
        val exercises = ArrayList<Exercise>()
        db.getConnection().use { conn ->
            conn.prepareStatement("SELECT * FROM $tableName").use { statement ->
                statement.executeQuery().use { resultSet ->
                    while (resultSet.next()) {
                        exercises.add(mapExerciseFromResultSet(resultSet))
                    }
                }
                return exercises
            }
        }
    }

    private fun mapExerciseFromResultSet(resultSet: ResultSet): Exercise {
        return Exercise(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("main_muscle"))
    }

}
