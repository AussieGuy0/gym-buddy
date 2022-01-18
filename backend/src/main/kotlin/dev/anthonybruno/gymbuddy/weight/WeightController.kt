package dev.anthonybruno.gymbuddy.weight

import dev.anthonybruno.gymbuddy.util.verifyUserAndGetIdFromPath
import io.javalin.http.Context

class WeightController(private val weightService: WeightService) {

    fun getWeights(context: Context) {
        val userId = context.verifyUserAndGetIdFromPath()
        val weights = weightService.getWeights(userId)
        context.json(weights)
    }

    fun addWeight(context: Context) {
        val userId = context.verifyUserAndGetIdFromPath()
        val weightToAdd = context.bodyAsClass<Weight>()
        val weight = weightService.addWeight(userId, weightToAdd);
        context.json(weight)
    }

}
