/*
 * This file is generated by jOOQ.
 */
package dev.anthonybruno.gymbuddy.db.jooq


import dev.anthonybruno.gymbuddy.db.jooq.sequences.*
import dev.anthonybruno.gymbuddy.db.jooq.tables.*
import org.jooq.Catalog
import org.jooq.Sequence
import org.jooq.Table
import org.jooq.impl.SchemaImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
class Public : SchemaImpl("public", DefaultCatalog.DEFAULT_CATALOG) {
    companion object {

        /**
         * The reference instance of <code>public</code>
         */
        val PUBLIC = Public()
    }

    /**
     * The table <code>public.exercises</code>.
     */
    val EXERCISES get() = Exercises.EXERCISES

    /**
     * The table <code>public.flyway_schema_history</code>.
     */
    val FLYWAY_SCHEMA_HISTORY get() = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY

    /**
     * The table <code>public.users</code>.
     */
    val USERS get() = Users.USERS

    /**
     * The table <code>public.weight_measurements</code>.
     */
    val WEIGHT_MEASUREMENTS get() = WeightMeasurements.WEIGHT_MEASUREMENTS

    /**
     * The table <code>public.workout_exercises</code>.
     */
    val WORKOUT_EXERCISES get() = WorkoutExercises.WORKOUT_EXERCISES

    /**
     * The table <code>public.workouts</code>.
     */
    val WORKOUTS get() = Workouts.WORKOUTS

    override fun getCatalog(): Catalog = DefaultCatalog.DEFAULT_CATALOG

    override fun getSequences(): List<Sequence<*>> = listOf(
        EXERCISES_ID_SEQ,
        USERS_ID_SEQ,
        WEIGHT_MEASUREMENTS_ID_SEQ,
        WORKOUT_EXERCISES_ID_SEQ,
        WORKOUTS_ID_SEQ
    )

    override fun getTables(): List<Table<*>> = listOf(
        Exercises.EXERCISES,
        FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
        Users.USERS,
        WeightMeasurements.WEIGHT_MEASUREMENTS,
        WorkoutExercises.WORKOUT_EXERCISES,
        Workouts.WORKOUTS
    )
}
