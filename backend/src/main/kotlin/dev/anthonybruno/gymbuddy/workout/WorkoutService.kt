package dev.anthonybruno.gymbuddy.workout

class WorkoutService(private val workoutRepository: WorkoutRepository) {

    fun getWorkouts(userId: Long): List<Workout> {
        return workoutRepository.getWorkouts(userId)
    }

    fun addWorkout(userId: Long, workout: AddWorkout): Workout {
        return workoutRepository.addWorkout(userId, workout)
    }

    fun getStats(userId: Long): WorkoutStats {
        return workoutRepository.getStats(userId)
    }
}
