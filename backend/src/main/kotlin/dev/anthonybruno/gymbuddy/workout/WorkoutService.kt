package dev.anthonybruno.gymbuddy.workout

import dev.anthonybruno.gymbuddy.exception.BadRequestException

class WorkoutService(private val workoutRepository: WorkoutRepository) {

    fun getWorkouts(userId: Int): List<Workout> {
        return workoutRepository.getWorkouts(userId)
    }

    fun addWorkout(userId: Int, workout: AddWorkout): Workout {
        if (workout.exercises.isEmpty()) {
            throw BadRequestException("There must be at least one exercise to save a workout.")
        }
        return workoutRepository.addWorkout(userId, workout)
    }

    fun getStats(userId: Int): WorkoutStats {
        return workoutRepository.getStats(userId)
    }

    fun getWorkoutsPerMonth(userId: Int): List<WorkoutsOnMonth> {
        return workoutRepository.getWorkoutsPerMonth(userId)
    }
}
