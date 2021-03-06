/*
 * This file is generated by jOOQ.
 */
package dev.anthonybruno.gymbuddy.db.jooq.tables.records


import dev.anthonybruno.gymbuddy.db.jooq.tables.Exercises

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record4
import org.jooq.Row4
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
class ExercisesRecord() : UpdatableRecordImpl<ExercisesRecord>(Exercises.EXERCISES), Record4<Int?, String?, String?, String?> {

    var id: Int?
        set(value) = set(0, value)
        get() = get(0) as Int?

    var name: String?
        set(value) = set(1, value)
        get() = get(1) as String?

    var description: String?
        set(value) = set(2, value)
        get() = get(2) as String?

    var mainMuscle: String?
        set(value) = set(3, value)
        get() = get(3) as String?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<Int?> = super.key() as Record1<Int?>

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row4<Int?, String?, String?, String?> = super.fieldsRow() as Row4<Int?, String?, String?, String?>
    override fun valuesRow(): Row4<Int?, String?, String?, String?> = super.valuesRow() as Row4<Int?, String?, String?, String?>
    override fun field1(): Field<Int?> = Exercises.EXERCISES.ID
    override fun field2(): Field<String?> = Exercises.EXERCISES.NAME
    override fun field3(): Field<String?> = Exercises.EXERCISES.DESCRIPTION
    override fun field4(): Field<String?> = Exercises.EXERCISES.MAIN_MUSCLE
    override fun component1(): Int? = id
    override fun component2(): String? = name
    override fun component3(): String? = description
    override fun component4(): String? = mainMuscle
    override fun value1(): Int? = id
    override fun value2(): String? = name
    override fun value3(): String? = description
    override fun value4(): String? = mainMuscle

    override fun value1(value: Int?): ExercisesRecord {
        id = value
        return this
    }

    override fun value2(value: String?): ExercisesRecord {
        name = value
        return this
    }

    override fun value3(value: String?): ExercisesRecord {
        description = value
        return this
    }

    override fun value4(value: String?): ExercisesRecord {
        mainMuscle = value
        return this
    }

    override fun values(value1: Int?, value2: String?, value3: String?, value4: String?): ExercisesRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        return this
    }

    /**
     * Create a detached, initialised ExercisesRecord
     */
    constructor(id: Int? = null, name: String? = null, description: String? = null, mainMuscle: String? = null): this() {
        this.id = id
        this.name = name
        this.description = description
        this.mainMuscle = mainMuscle
    }
}
