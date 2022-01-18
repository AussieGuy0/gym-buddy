package dev.anthonybruno.gymbuddy.weight

import dev.anthonybruno.gymbuddy.db.Database
import dev.anthonybruno.gymbuddy.db.jooq.tables.records.WeightMeasurementsRecord
import dev.anthonybruno.gymbuddy.db.jooq.tables.references.WEIGHT_MEASUREMENTS
import java.time.ZoneOffset

interface WeightRepository {

    fun getWeights(userId: Int): List<Weight>

    fun addWeight(userId: Int, weight: Weight): Weight

}

class DbWeightRepository(private val database: Database): WeightRepository {

    override fun getWeights(userId: Int): List<Weight> {
        return database.jooq()
            .selectFrom(WEIGHT_MEASUREMENTS)
            .where(WEIGHT_MEASUREMENTS.USER_ID.eq(userId))
            .fetch()
            .map { mapRecordToWeight(it) }
    }

    override fun addWeight(userId: Int, weight: Weight): Weight {
        return WEIGHT_MEASUREMENTS.newRecord().apply {
            this.userId = userId
            this.date = weight.date.atOffset(ZoneOffset.UTC)
            store()
        }.map { mapRecordToWeight(it as WeightMeasurementsRecord) }
    }

    private fun mapRecordToWeight(record: WeightMeasurementsRecord): Weight {
       return record.map {  Weight(it[WEIGHT_MEASUREMENTS.WEIGHT]!!.toDouble(), it[WEIGHT_MEASUREMENTS.DATE]!!.toInstant()) }
    }

}