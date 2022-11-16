package dev.anthonybruno.gymbuddy.workout

import dev.anthonybruno.gymbuddy.util.verifyUserAndGetIdFromPath
import io.javalin.http.Context

class WorkoutController(private val workoutService: WorkoutService) {

    fun addWorkout(context: Context) {
        val userId = context.verifyUserAndGetIdFromPath();
        val workout = context.bodyAsClass(AddWorkout::class.java)
        val savedWorkout = workoutService.addWorkout(userId, workout)
        context.json(savedWorkout)
    }

    fun getWorkouts(context: Context) {
        val userId = context.verifyUserAndGetIdFromPath();
        val workouts = workoutService.getWorkouts(userId)
        context.json(workouts)
    }

    fun getStats(context: Context) {
        val userId = context.verifyUserAndGetIdFromPath();
        val workoutStats = workoutService.getStats(userId)
        context.json(workoutStats)
    }

}
