package dev.anthonybruno.gymbuddy.workout

import java.time.Instant

data class Workout(val id: Int,
                   val date: Instant,
                   val title: String?,
                   val description: String?,
                   val exercises: List<WorkoutExerciseDto>)

data class WorkoutExerciseDto(val name: String, val weight: Int?, val sets: Int, val reps: Int)

data class Exercise(val id: Int, val name: String, val description: String?, val mainMuscle: String)

data class AddWorkout(val title: String?,
                      val description: String?,
                      val exercises: List<AddExercise>)

data class AddExercise(val id: Int, val sets: Int, val reps: Int, val weight: Int?)

data class WorkoutStats(
        val lastWorkout: Instant?,
        val commonExercise: String,
        val workoutsLast30Days: Int
)
