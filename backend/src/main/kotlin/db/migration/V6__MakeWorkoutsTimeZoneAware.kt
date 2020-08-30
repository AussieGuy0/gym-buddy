package db.migration

import dev.anthonybruno.gymbuddy.db.DefaultJdbcHelper
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneId

class V6__MakeWorkoutsTimeZoneAware : BaseJavaMigration() {

    override fun migrate(context: Context) {
        val helper = DefaultJdbcHelper(context.configuration.dataSource)

        // Firstly, let's get all users and their timezone
        val records = helper.query({ conn ->
            conn.prepareStatement("""
                SELECT u.id, u.timezone
                FROM users u;
                """)
        }, { rs, ctx ->
            Record(rs.getLong("id"), rs.getString("timezone"))
        })

        // Next, we update each workout with timezone information
        records.forEach { r ->
            helper.execute {
                it.prepareStatement("""
                   UPDATE workouts
                   SET timezone = ?
                   WHERE user_id = ?
                """).apply {
                    setString(1, r.timeZoneStr)
                    setLong(2, r.userId)
                }
            }
        }

        // Finally, we can drop the old date column and replace it with date_tz
        helper.execute {
            it.prepareStatement("""
                ALTER TABLE workouts
                DROP COLUMN date;

                ALTER TABLE workouts
                RENAME COLUMN date_tz to date;
            """)
        }
    }
}

private class Record(val userId: Long, val timeZoneStr: String) {

    val timezone = ZoneId.of(timeZoneStr)

}