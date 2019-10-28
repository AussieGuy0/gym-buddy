package dev.anthonybruno.gymbuddy.workout

import dev.anthonybruno.gymbuddy.common.Repository
import dev.anthonybruno.gymbuddy.util.ensureUserSignedIn
import io.javalin.http.Context
import java.sql.ResultSet
import java.util.ArrayList
import kotlin.system.exitProcess

class ExerciseController(private val exerciseService: ExerciseService = ExerciseServiceImpl()) {

    fun getExercises(ctx: Context) {
        ensureUserSignedIn(ctx)
        ctx.json(exerciseService.getExercises())
    }

}

interface ExerciseService {

    fun getExercises(): List<Exercise>
}

class ExerciseServiceImpl(private val exerciseRepository: ExerciseRepository = ExerciseRepository()): ExerciseService {

    override fun getExercises(): List<Exercise> {
        return exerciseRepository.getExercises();
    }

}

class ExerciseRepository: Repository("exercises") {

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
