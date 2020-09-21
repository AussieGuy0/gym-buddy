package dev.anthonybruno.gymbuddy.graph

import com.fasterxml.jackson.annotation.JsonProperty

enum class GraphType {
    @JsonProperty("bar")
    BAR
}

data class Graph(val data: List<GraphData>, val labels: GraphLabels)

data class GraphData(val type: GraphType, val x: List<String>, val y: List<Int>);

data class GraphLabels(val title: String, val xAxis: AxisLabel, val yAxis: AxisLabel);

data class AxisLabel(val text: String)