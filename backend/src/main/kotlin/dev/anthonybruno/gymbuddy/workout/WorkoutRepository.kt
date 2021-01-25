package dev.anthonybruno.gymbuddy.workout

interface WorkoutRepository {

    fun getWorkouts(userId: Int): List<Workout>

    fun getWorkout(workoutId: Int): Workout?

    fun addWorkout(userId: Int, workout: AddWorkout): Workout

    fun getStats(userId: Int): WorkoutStats

    fun getWorkoutsPerMonth(userId: Int): List<WorkoutsOnMonth>

}