package dev.anthonybruno.gymbuddy.graph

import dev.anthonybruno.gymbuddy.workout.WorkoutService

class GraphService(private val workoutService: WorkoutService) {

    fun getRandomGraph(userId: Int): Graph {
        // Definitely random...
        val xAxis = mutableListOf<String>()
        val yAxis = mutableListOf<Int>()
        for (workoutsOnMonth in workoutService.getWorkoutsPerMonth(userId)) {
            xAxis.add(workoutsOnMonth.month.toString())
            yAxis.add(workoutsOnMonth.workouts)
        }
        return Graph(
                data = listOf(GraphData(type = GraphType.BAR, x = xAxis, y = yAxis)),
                labels = GraphLabels("Workouts per month", AxisLabel("Month"), AxisLabel("Workout Number"))
        )
    }

}