package dev.anthonybruno.gymbuddy.workout

import dev.anthonybruno.gymbuddy.exception.UnauthorisedException
import dev.anthonybruno.gymbuddy.util.json.Json
import dev.anthonybruno.gymbuddy.auth.SessionUtils
import dev.anthonybruno.gymbuddy.util.getUserIdFromPath
import io.javalin.http.Context

class WorkoutController(private val workoutService: WorkoutService = WorkoutServiceImpl()) {


    fun addWorkout(context: Context) {
        val userId = getUserIdFromPath(context)
        checkUserIsAccessingOwnWorkouts(userId, context)
        val workout = Json.intoClass(context.body(), Workout::class.java)
        val savedWorkout = workoutService.addWorkout(userId, workout)
        context.json(savedWorkout)
    }

    fun getWorkouts(context: Context) {
        val userId = getUserIdFromPath(context)
        checkUserIsAccessingOwnWorkouts(userId, context)
        val workouts = workoutService.getWorkouts(userId)
        context.json(workouts)
    }

    private fun checkUserIsAccessingOwnWorkouts(userId: Long, context: Context) {
        val userDetails = SessionUtils.getSession(context)
        if (userId != userDetails!!.id) {
            throw UnauthorisedException("Can't access someone else's workouts!")
        }
    }

}
