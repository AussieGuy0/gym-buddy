package dev.anthonybruno.gymbuddy.workout

class WorkoutServiceImpl : WorkoutService {

    private val workoutRepository = WorkoutRepository()

    override fun getWorkouts(userId: Int): List<Workout> {
        return workoutRepository.getWorkouts(userId)
    }

    override fun addWorkout(userId: Int, workout: Workout): Workout {
        return workoutRepository.addWorkout(userId, workout)
    }
}
