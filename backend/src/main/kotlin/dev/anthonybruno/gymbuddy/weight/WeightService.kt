package dev.anthonybruno.gymbuddy.weight

class WeightService(private val weightRepository: WeightRepository) {

    fun getWeights(userId: Int): List<Weight> {
        return weightRepository.getWeights(userId)
    }

    fun addWeight(userId: Int, weight: Weight): Weight {
        return weightRepository.addWeight(userId, weight)
    }

}
