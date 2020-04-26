package dev.anthonybruno.gymbuddy.workout

import dev.anthonybruno.gymbuddy.Server
import dev.anthonybruno.gymbuddy.util.ensureUserSignedIn
import io.javalin.http.Context
import java.sql.ResultSet
import java.util.ArrayList
import kotlin.system.exitProcess

class ExerciseController(private val exerciseService: ExerciseService = ExerciseService()) {

    fun getExercises(ctx: Context) {
        ctx.ensureUserSignedIn()
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
    private val dbHelper = db.getHelper()

    fun getExercises(): List<Exercise> {
        return dbHelper.query({
            it.prepareStatement("SELECT * FROM $tableName")
        }, { rs, _ ->
            mapExerciseFromResultSet(rs)
        })
    }

    private fun mapExerciseFromResultSet(resultSet: ResultSet): Exercise {
        return Exercise(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("main_muscle"))
    }

}
