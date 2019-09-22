package dev.anthonybruno.gymbuddy.workout

interface WorkoutService {

    fun getWorkouts(userId: Int): List<Workout>

    fun addWorkout(userId: Int, workout: Workout): Workout
}
