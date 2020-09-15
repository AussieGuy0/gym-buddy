package dev.anthonybruno.gymbuddy.graph

import dev.anthonybruno.gymbuddy.util.getUserIdFromPath
import dev.anthonybruno.gymbuddy.util.verifyUserAndGetIdFromPath
import io.javalin.http.Context

class GraphController(private val graphService: GraphService) {

    fun getRandomGraph(context: Context) {
        val userId = context.verifyUserAndGetIdFromPath()
        val randomGraph = graphService.getRandomGraph(userId)
        context.json(randomGraph)
    }

}