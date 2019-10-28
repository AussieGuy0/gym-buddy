package dev.anthonybruno.gymbuddy.workout

class WorkoutServiceImpl : WorkoutService {

    private val workoutRepository = WorkoutRepository()

    override fun getWorkouts(userId: Long): List<Workout> {
        return workoutRepository.getWorkouts(userId)
    }

    override fun addWorkout(userId: Long, workout: Workout): Workout {
        return workoutRepository.addWorkout(userId, workout)
    }
}
