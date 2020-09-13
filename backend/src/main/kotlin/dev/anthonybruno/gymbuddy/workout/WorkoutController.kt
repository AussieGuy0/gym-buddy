package dev.anthonybruno.gymbuddy.workout

import dev.anthonybruno.gymbuddy.exception.UnauthorisedException
import dev.anthonybruno.gymbuddy.auth.SessionUtils
import dev.anthonybruno.gymbuddy.util.ensureUserSignedIn
import dev.anthonybruno.gymbuddy.util.getSession
import dev.anthonybruno.gymbuddy.util.getUserIdFromPath
import io.javalin.http.Context

class WorkoutController(private val workoutService: WorkoutService) {

    fun addWorkout(context: Context) {
        context.ensureUserSignedIn()
        val userId = context.getUserIdFromPath()
        checkUserIsAccessingOwnWorkouts(userId, context)
        val workout = context.body<AddWorkout>()
        val savedWorkout = workoutService.addWorkout(userId, workout)
        context.json(savedWorkout)
    }

    fun getWorkouts(context: Context) {
        context.ensureUserSignedIn()
        val userId = context.getUserIdFromPath()
        checkUserIsAccessingOwnWorkouts(userId, context)
        val workouts = workoutService.getWorkouts(userId)
        context.json(workouts)
    }

    fun getStats(context: Context) {
        context.ensureUserSignedIn()
        val userId = context.getUserIdFromPath()
        checkUserIsAccessingOwnWorkouts(userId, context)
        val workoutStats = workoutService.getStats(userId)
        context.json(workoutStats)
    }

    private fun checkUserIsAccessingOwnWorkouts(userId: Long, context: Context) {
        val userDetails = context.getSession()
        if (userId != userDetails!!.id) {
            throw UnauthorisedException("Can't access someone else's workouts!")
        }
    }

}
