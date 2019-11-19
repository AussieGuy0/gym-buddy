package dev.anthonybruno.gymbuddy.workout

interface WorkoutService {

    fun getWorkouts(userId: Long): List<Workout>

    fun addWorkout(userId: Long, workout: AddWorkout): Workout
}
