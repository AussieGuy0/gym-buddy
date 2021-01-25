/*
 * This file is generated by jOOQ.
 */
package dev.anthonybruno.gymbuddy.db.jooq.indexes


import dev.anthonybruno.gymbuddy.db.jooq.tables.FlywaySchemaHistory
import dev.anthonybruno.gymbuddy.db.jooq.tables.Users

import org.jooq.Index
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// INDEX definitions
// -------------------------------------------------------------------------

val FLYWAY_SCHEMA_HISTORY_S_IDX: Index = Internal.createIndex(DSL.name("flyway_schema_history_s_idx"), FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, arrayOf(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.SUCCESS), false)
val IDX_USER_EMAIL: Index = Internal.createIndex(DSL.name("idx_user_email"), Users.USERS, arrayOf(Users.USERS.EMAIL), false)
