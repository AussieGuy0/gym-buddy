package dev.anthonybruno.gymbuddy.workout

import dev.anthonybruno.gymbuddy.db.Database
import dev.anthonybruno.gymbuddy.db.jooq.tables.records.ExercisesRecord
import dev.anthonybruno.gymbuddy.db.jooq.tables.references.EXERCISES
import dev.anthonybruno.gymbuddy.util.ensureUserSignedIn
import io.javalin.http.Context

class ExerciseController(private val exerciseService: ExerciseService) {

    fun getExercises(ctx: Context) {
        ctx.ensureUserSignedIn()
        ctx.json(exerciseService.getExercises())
    }

}

class ExerciseService(private val exerciseRepository: DbExerciseRepository) {

    fun getExercises(): List<Exercise> {
        return exerciseRepository.getExercises();
    }

}

class DbExerciseRepository(private val db: Database) {

    fun getExercises(): List<Exercise> {
        return db.jooq().fetch(EXERCISES).map(this::mapExerciseFromRecord)
    }

    private fun mapExerciseFromRecord(record: ExercisesRecord): Exercise {
        return Exercise(record.id!!,
                record.name!!,
                record.description,
                record.mainMuscle!!)
    }

}
