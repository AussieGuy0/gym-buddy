/*
 * This file is generated by jOOQ.
 */
package dev.anthonybruno.gymbuddy.db.jooq.tables.records


import dev.anthonybruno.gymbuddy.db.jooq.tables.WorkoutExercises

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record6
import org.jooq.Row6
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
class WorkoutExercisesRecord() : UpdatableRecordImpl<WorkoutExercisesRecord>(WorkoutExercises.WORKOUT_EXERCISES), Record6<Int?, Int?, Int?, Int?, Int?, Int?> {

    var id: Int?
        set(value) = set(0, value)
        get() = get(0) as Int?

    var workoutId: Int?
        set(value) = set(1, value)
        get() = get(1) as Int?

    var exerciseId: Int?
        set(value) = set(2, value)
        get() = get(2) as Int?

    var sets: Int?
        set(value) = set(3, value)
        get() = get(3) as Int?

    var reps: Int?
        set(value) = set(4, value)
        get() = get(4) as Int?

    var weight: Int?
        set(value) = set(5, value)
        get() = get(5) as Int?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<Int?> = super.key() as Record1<Int?>

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row6<Int?, Int?, Int?, Int?, Int?, Int?> = super.fieldsRow() as Row6<Int?, Int?, Int?, Int?, Int?, Int?>
    override fun valuesRow(): Row6<Int?, Int?, Int?, Int?, Int?, Int?> = super.valuesRow() as Row6<Int?, Int?, Int?, Int?, Int?, Int?>
    override fun field1(): Field<Int?> = WorkoutExercises.WORKOUT_EXERCISES.ID
    override fun field2(): Field<Int?> = WorkoutExercises.WORKOUT_EXERCISES.WORKOUT_ID
    override fun field3(): Field<Int?> = WorkoutExercises.WORKOUT_EXERCISES.EXERCISE_ID
    override fun field4(): Field<Int?> = WorkoutExercises.WORKOUT_EXERCISES.SETS
    override fun field5(): Field<Int?> = WorkoutExercises.WORKOUT_EXERCISES.REPS
    override fun field6(): Field<Int?> = WorkoutExercises.WORKOUT_EXERCISES.WEIGHT
    override fun component1(): Int? = id
    override fun component2(): Int? = workoutId
    override fun component3(): Int? = exerciseId
    override fun component4(): Int? = sets
    override fun component5(): Int? = reps
    override fun component6(): Int? = weight
    override fun value1(): Int? = id
    override fun value2(): Int? = workoutId
    override fun value3(): Int? = exerciseId
    override fun value4(): Int? = sets
    override fun value5(): Int? = reps
    override fun value6(): Int? = weight

    override fun value1(value: Int?): WorkoutExercisesRecord {
        id = value
        return this
    }

    override fun value2(value: Int?): WorkoutExercisesRecord {
        workoutId = value
        return this
    }

    override fun value3(value: Int?): WorkoutExercisesRecord {
        exerciseId = value
        return this
    }

    override fun value4(value: Int?): WorkoutExercisesRecord {
        sets = value
        return this
    }

    override fun value5(value: Int?): WorkoutExercisesRecord {
        reps = value
        return this
    }

    override fun value6(value: Int?): WorkoutExercisesRecord {
        weight = value
        return this
    }

    override fun values(value1: Int?, value2: Int?, value3: Int?, value4: Int?, value5: Int?, value6: Int?): WorkoutExercisesRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        this.value5(value5)
        this.value6(value6)
        return this
    }

    /**
     * Create a detached, initialised WorkoutExercisesRecord
     */
    constructor(id: Int? = null, workoutId: Int? = null, exerciseId: Int? = null, sets: Int? = null, reps: Int? = null, weight: Int? = null): this() {
        this.id = id
        this.workoutId = workoutId
        this.exerciseId = exerciseId
        this.sets = sets
        this.reps = reps
        this.weight = weight
    }
}
