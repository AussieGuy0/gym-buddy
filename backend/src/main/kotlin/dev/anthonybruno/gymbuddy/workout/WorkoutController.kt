package dev.anthonybruno.gymbuddy.workout

import dev.anthonybruno.gymbuddy.exception.HttpException
import dev.anthonybruno.gymbuddy.exception.UnauthorisedException
import dev.anthonybruno.gymbuddy.user.UserDetails
import dev.anthonybruno.gymbuddy.util.json.Json
import dev.anthonybruno.gymbuddy.auth.SessionUtils
import io.javalin.http.Context

class WorkoutController @JvmOverloads constructor(private val workoutService: WorkoutService = WorkoutServiceImpl()) {


    fun addWorkout(context: Context) {
        val userId = getUserIdFromRequest(context)
        checkUserIsAccessingOwnWorkouts(userId, context)
        val workout = Json.intoClass(context.body(), Workout::class.java)
        val savedWorkout = workoutService.addWorkout(userId, workout)
        context.json(savedWorkout)
    }

    fun getWorkouts(context: Context) {
        val userId = getUserIdFromRequest(context)
        checkUserIsAccessingOwnWorkouts(userId, context)
        val workouts = workoutService.getWorkouts(userId)
        context.json(workouts)
    }

    private fun checkUserIsAccessingOwnWorkouts(userId: Int, context: Context) {
        val userDetails = SessionUtils.getSession(context)
        if (userId.toLong() != userDetails!!.id) {
            throw UnauthorisedException("Can't access someone else's workouts!")
        }
    }

    private fun getUserIdFromRequest(context: Context): Int {
        val userId = context.pathParam("userId")
        if (userId.isEmpty()) {
            throw HttpException(400, "Need userId in path")
        }
        try {
            return Integer.parseInt(userId)
        } catch (e: NumberFormatException) {
            throw HttpException(400, "userId provided is not a number!")
        }

    }
}
