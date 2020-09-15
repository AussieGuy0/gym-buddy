package dev.anthonybruno.gymbuddy.graph

import dev.anthonybruno.gymbuddy.workout.WorkoutService

class GraphService(private val workoutService: WorkoutService) {

    fun getRandomGraph(userId: Long): Graph {
        return Graph(
                data = listOf(GraphData(type = GraphType.BAR, x = listOf("Jan", "Feb", "Mar"), y = listOf(3, 5, 10))),
                labels = GraphLabels("Workouts per month", AxisLabel("Month"), AxisLabel("Workout Number"))
        )
        //TODO!
//        workoutService.getWorkoutsPerMonth();
    }

}