package dev.anthonybruno.gymbuddy.workout

interface WorkoutRepository {

    fun getWorkouts(userId: Long): List<Workout>

    fun getWorkout(workoutId: Int): Workout?

    fun addWorkout(userId: Long, workout: AddWorkout): Workout

}