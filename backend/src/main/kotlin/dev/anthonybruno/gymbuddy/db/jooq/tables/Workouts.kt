/*
 * This file is generated by jOOQ.
 */
package dev.anthonybruno.gymbuddy.db.jooq.tables


import dev.anthonybruno.gymbuddy.db.jooq.Public
import dev.anthonybruno.gymbuddy.db.jooq.keys.WORKOUTS_PKEY
import dev.anthonybruno.gymbuddy.db.jooq.keys.WORKOUTS__WORKOUTS_USER_ID_FKEY
import dev.anthonybruno.gymbuddy.db.jooq.tables.records.WorkoutsRecord

import java.time.OffsetDateTime

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Identity
import org.jooq.Name
import org.jooq.Record
import org.jooq.Row6
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
class Workouts(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, WorkoutsRecord>?,
    aliased: Table<WorkoutsRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<WorkoutsRecord>(
    alias,
    Public.PUBLIC,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>public.workouts</code>
         */
        val WORKOUTS = Workouts()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<WorkoutsRecord> = WorkoutsRecord::class.java

    /**
     * The column <code>public.workouts.id</code>.
     */
    val ID: TableField<WorkoutsRecord, Int?> = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "")

    /**
     * The column <code>public.workouts.user_id</code>.
     */
    val USER_ID: TableField<WorkoutsRecord, Int?> = createField(DSL.name("user_id"), SQLDataType.INTEGER.nullable(false), this, "")

    /**
     * The column <code>public.workouts.title</code>.
     */
    val TITLE: TableField<WorkoutsRecord, String?> = createField(DSL.name("title"), SQLDataType.VARCHAR(100), this, "")

    /**
     * The column <code>public.workouts.description</code>.
     */
    val DESCRIPTION: TableField<WorkoutsRecord, String?> = createField(DSL.name("description"), SQLDataType.CLOB, this, "")

    /**
     * The column <code>public.workouts.date</code>.
     */
    val DATE: TableField<WorkoutsRecord, OffsetDateTime?> = createField(DSL.name("date"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "")

    /**
     * The column <code>public.workouts.timezone</code>.
     */
    val TIMEZONE: TableField<WorkoutsRecord, String?> = createField(DSL.name("timezone"), SQLDataType.CLOB, this, "")

    private constructor(alias: Name, aliased: Table<WorkoutsRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<WorkoutsRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>public.workouts</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.workouts</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.workouts</code> table reference
     */
    constructor(): this(DSL.name("workouts"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, WorkoutsRecord>): this(Internal.createPathAlias(child, key), child, key, WORKOUTS, null)
    override fun getSchema(): Schema = Public.PUBLIC
    override fun getIdentity(): Identity<WorkoutsRecord, Int?> = super.getIdentity() as Identity<WorkoutsRecord, Int?>
    override fun getPrimaryKey(): UniqueKey<WorkoutsRecord> = WORKOUTS_PKEY
    override fun getKeys(): List<UniqueKey<WorkoutsRecord>> = listOf(WORKOUTS_PKEY)
    override fun getReferences(): List<ForeignKey<WorkoutsRecord, *>> = listOf(WORKOUTS__WORKOUTS_USER_ID_FKEY)
    fun users(): Users = Users(this, WORKOUTS__WORKOUTS_USER_ID_FKEY)
    override fun `as`(alias: String): Workouts = Workouts(DSL.name(alias), this)
    override fun `as`(alias: Name): Workouts = Workouts(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Workouts = Workouts(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Workouts = Workouts(name, null)

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row6<Int?, Int?, String?, String?, OffsetDateTime?, String?> = super.fieldsRow() as Row6<Int?, Int?, String?, String?, OffsetDateTime?, String?>
}
